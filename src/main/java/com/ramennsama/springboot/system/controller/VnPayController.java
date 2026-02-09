package com.ramennsama.springboot.system.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ramennsama.springboot.system.entity.Order;
import com.ramennsama.springboot.system.entity.Payment;
import com.ramennsama.springboot.system.entity.enumtype.OrderStatus;
import com.ramennsama.springboot.system.repository.OrderRepository;
import com.ramennsama.springboot.system.repository.PaymentRepository;
import com.ramennsama.springboot.system.service.EmailService;
import com.ramennsama.springboot.system.service.VnPayService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/vnpay")
@RequiredArgsConstructor
@Tag(name = "VNPay Payment", description = "APIs for VNPay payment integration")
@Slf4j
public class VnPayController {

    private final VnPayService vnPayService;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final EmailService emailService;

    @PostMapping("/create-payment/{orderId}")
    @ResponseBody
    @Operation(
            summary = "Create VNPay payment URL",
            description = "Creates a payment URL and redirects user to VNPay payment gateway"
    )

    public ResponseEntity<Map<String, String>> createPayment(
            @Parameter(description = "Order ID to create payment for", required = true)
            @PathVariable Long orderId) {
        
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Order is not in PENDING status");
        }

        int amount = order.getTotalAmount().intValue();
        String txnRef = order.getVnpTxnRef();

        String paymentUrl = vnPayService.createPaymentUrl(amount, txnRef);

        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);
        response.put("txnRef", txnRef);
        response.put("amount", String.valueOf(amount));

        //log.info("PAYMENT URL : {}", paymentUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/return")
    @Operation(
            summary = "VNPay payment return URL",
            description = "Handles the return from VNPay payment gateway"
    )
    public String paymentReturn(@RequestParam Map<String, String> params, Model model) {
        
        boolean isValid = vnPayService.verifyReturn(params);

        if (!isValid) {
            model.addAttribute("message", "Invalid signature");
            return "payment-error";
        }

        String txnRef = params.get("vnp_TxnRef");
        String responseCode = params.get("vnp_ResponseCode");

        Order order = orderRepository.findByVnpTxnRef(txnRef)
                .orElseThrow(() -> new RuntimeException("Order not found with txnRef: " + txnRef));

        // Response code 00 means success
        if ("00".equals(responseCode)) {
            // Update order status
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);

            // Create payment record
            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setVnpTxnRef(txnRef);
            payment.setAmount(order.getTotalAmount());
            payment.setResponseCode(responseCode);
            payment.setPaidAt(LocalDateTime.now());
            paymentRepository.save(payment);

            // Send payment success email
            emailService.sendPaymentSuccessEmail(order, order.getUser().getEmail());

            model.addAttribute("txnRef", txnRef);
            model.addAttribute("orderId", order.getId());
            model.addAttribute("amount", order.getTotalAmount());
            
            return "payment-success";
        } else {
            model.addAttribute("txnRef", txnRef);
            model.addAttribute("code", responseCode);
            return "payment-error";
        }
    }

    @GetMapping("/check-payment/{txnRef}")
    @ResponseBody
    @Operation(
            summary = "Check payment status",
            description = "Checks the payment status for a transaction reference"
    )
    public ResponseEntity<Map<String, Object>> checkPayment(
            @Parameter(description = "Transaction reference", required = true)
            @PathVariable String txnRef) {
        
        Order order = orderRepository.findByVnpTxnRef(txnRef)
                .orElseThrow(() -> new RuntimeException("Order not found with txnRef: " + txnRef));

        Payment payment = paymentRepository.findByVnpTxnRef(txnRef).orElse(null);

        Map<String, Object> response = new HashMap<>();
        response.put("txnRef", txnRef);
        response.put("orderStatus", order.getStatus());
        response.put("totalAmount", order.getTotalAmount());
        
        if (payment != null) {
            response.put("paymentStatus", "PAID");
            response.put("paidAt", payment.getPaidAt());
            response.put("responseCode", payment.getResponseCode());
        } else {
            response.put("paymentStatus", "PENDING");
        }

        return ResponseEntity.ok(response);
    }
}

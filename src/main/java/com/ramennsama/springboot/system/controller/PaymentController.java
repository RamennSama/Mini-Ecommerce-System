package com.ramennsama.springboot.system.controller;

import com.ramennsama.springboot.system.dto.response.PaymentResponse;
import com.ramennsama.springboot.system.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Management", description = "APIs for managing payments in E-Commerce system")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    @Operation(
            summary = "Get all payments",
            description = "Returns a complete list of all payments in the system"
    )
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        List<PaymentResponse> payments = paymentService.findAll();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get payment by ID",
            description = "Returns detailed information of a payment based on ID"
    )
    public ResponseEntity<PaymentResponse> getPaymentById(
            @Parameter(description = "ID of the payment to find", required = true)
            @PathVariable Long id) {
        PaymentResponse payment = paymentService.findById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/order/{orderId}")
    @Operation(
            summary = "Get payment by order ID",
            description = "Returns payment information for a specific order"
    )
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(
            @Parameter(description = "ID of the order", required = true)
            @PathVariable Long orderId) {
        PaymentResponse payment = paymentService.findByOrderId(orderId);
        return ResponseEntity.ok(payment);
    }
}

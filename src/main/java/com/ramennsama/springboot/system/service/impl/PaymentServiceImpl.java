package com.ramennsama.springboot.system.service.impl;

import com.ramennsama.springboot.system.dto.response.PaymentResponse;
import com.ramennsama.springboot.system.entity.Payment;
import com.ramennsama.springboot.system.mapper.PaymentMapper;
import com.ramennsama.springboot.system.repository.PaymentRepository;
import com.ramennsama.springboot.system.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toPaymentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        return paymentMapper.toPaymentResponse(payment);
    }

    @Override
    public PaymentResponse findByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order id: " + orderId));
        return paymentMapper.toPaymentResponse(payment);
    }
}

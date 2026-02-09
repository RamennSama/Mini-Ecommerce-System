package com.ramennsama.springboot.system.service;

import com.ramennsama.springboot.system.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {
    List<PaymentResponse> findAll();
    PaymentResponse findById(Long id);
    PaymentResponse findByOrderId(Long orderId);
}

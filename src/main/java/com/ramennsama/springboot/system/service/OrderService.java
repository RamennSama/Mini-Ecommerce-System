package com.ramennsama.springboot.system.service;

import com.ramennsama.springboot.system.dto.request.OrderRequest;
import com.ramennsama.springboot.system.dto.response.OrderResponse;
import com.ramennsama.springboot.system.entity.enumtype.OrderStatus;

import java.util.List;

public interface OrderService {
    List<OrderResponse> findAll();
    OrderResponse findById(Long id);
    OrderResponse createOrder(OrderRequest orderRequest);
    List<OrderResponse> findByUserId(Long userId);
    List<OrderResponse> findByStatus(OrderStatus status);
    OrderResponse updateOrderStatus(Long id, OrderStatus status);
}

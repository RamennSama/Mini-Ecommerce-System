package com.ramennsama.springboot.system.dto.response;

import com.ramennsama.springboot.system.entity.enumtype.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    
    private Long id;
    
    private UserResponse user;
    
    private BigDecimal totalAmount;
    
    private OrderStatus status;
    
    private String vnpTxnRef;
    
    private LocalDateTime createdAt;
    
    private List<OrderItemResponse> items;
}

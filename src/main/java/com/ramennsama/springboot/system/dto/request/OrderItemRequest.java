package com.ramennsama.springboot.system.dto.request;

import lombok.Data;

@Data
public class OrderItemRequest {
    
    private Long productId;
    
    private Integer quantity;
}

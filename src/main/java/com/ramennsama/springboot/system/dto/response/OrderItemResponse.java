package com.ramennsama.springboot.system.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    
    private Long id;
    
    private ProductResponse product;
    
    private Integer quantity;
    
    private BigDecimal price;
}

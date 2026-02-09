package com.ramennsama.springboot.system.dto.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProductRequest {
    
    private String name;
    
    private BigDecimal price;
    
    private Integer stock;
    
    private List<Long> categoryIds;
}

package com.ramennsama.springboot.system.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductResponse {
    
    private Long id;
    
    private String name;
    
    private BigDecimal price;
    
    private Integer stock;
    
    private List<CategoryResponse> categories;
}

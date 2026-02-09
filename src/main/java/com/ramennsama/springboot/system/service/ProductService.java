package com.ramennsama.springboot.system.service;

import com.ramennsama.springboot.system.dto.request.ProductRequest;
import com.ramennsama.springboot.system.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAll();
    ProductResponse findById(Long id);
    ProductResponse save(ProductRequest productRequest);
    ProductResponse update(Long id, ProductRequest productRequest);
    void deleteById(Long id);
    List<ProductResponse> searchByName(String name);
}

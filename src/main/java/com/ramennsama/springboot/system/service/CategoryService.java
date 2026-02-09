package com.ramennsama.springboot.system.service;

import com.ramennsama.springboot.system.dto.request.CategoryRequest;
import com.ramennsama.springboot.system.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAll();
    CategoryResponse findById(Long id);
    CategoryResponse save(CategoryRequest categoryRequest);
    CategoryResponse update(Long id, CategoryRequest categoryRequest);
    void deleteById(Long id);
}

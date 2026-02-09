package com.ramennsama.springboot.system.service.impl;

import com.ramennsama.springboot.system.dto.request.ProductRequest;
import com.ramennsama.springboot.system.dto.response.ProductResponse;
import com.ramennsama.springboot.system.entity.Category;
import com.ramennsama.springboot.system.entity.Product;
import com.ramennsama.springboot.system.mapper.ProductMapper;
import com.ramennsama.springboot.system.repository.CategoryRepository;
import com.ramennsama.springboot.system.repository.ProductRepository;
import com.ramennsama.springboot.system.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productMapper.toProductResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse save(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        
        if (productRequest.getCategoryIds() != null && !productRequest.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(productRequest.getCategoryIds());
            product.setCategories(categories);
        }
        
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        productMapper.updateProduct(existingProduct, productRequest);
        
        if (productRequest.getCategoryIds() != null && !productRequest.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(productRequest.getCategoryIds());
            existingProduct.setCategories(categories);
        }
        
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toProductResponse(updatedProduct);
    }

    @Override
    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}

package com.ramennsama.springboot.system.controller;

import com.ramennsama.springboot.system.dto.request.ProductRequest;
import com.ramennsama.springboot.system.dto.response.ProductResponse;
import com.ramennsama.springboot.system.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "APIs for managing products in E-Commerce system")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Returns a complete list of all products in the system"
    )
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get product by ID",
            description = "Returns detailed information of a product based on ID"
    )
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "ID of the product to find", required = true)
            @PathVariable Long id) {
        ProductResponse product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search products by name",
            description = "Returns products matching the search term"
    )
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @Parameter(description = "Product name to search", required = true)
            @RequestParam String name) {
        List<ProductResponse> products = productService.searchByName(name);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    @Operation(
            summary = "Create new product",
            description = "Creates a new product in the system"
    )
    public ResponseEntity<ProductResponse> createProduct(
            @Parameter(description = "Product information to create", required = true)
            @RequestBody ProductRequest productRequest) {
        ProductResponse savedProduct = productService.save(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update product information",
            description = "Updates information of an existing product"
    )
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated information", required = true)
            @RequestBody ProductRequest productRequest) {
        ProductResponse updatedProduct = productService.update(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete product",
            description = "Deletes a product from the system"
    )
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID of the product to delete", required = true)
            @PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

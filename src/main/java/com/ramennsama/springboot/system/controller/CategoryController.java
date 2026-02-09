package com.ramennsama.springboot.system.controller;

import com.ramennsama.springboot.system.dto.request.CategoryRequest;
import com.ramennsama.springboot.system.dto.response.CategoryResponse;
import com.ramennsama.springboot.system.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category Management", description = "APIs for managing categories in E-Commerce system")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Returns a complete list of all categories in the system"
    )
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get category by ID",
            description = "Returns detailed information of a category based on ID"
    )
    public ResponseEntity<CategoryResponse> getCategoryById(
            @Parameter(description = "ID of the category to find", required = true)
            @PathVariable Long id) {
        CategoryResponse category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    @Operation(
            summary = "Create new category",
            description = "Creates a new category in the system"
    )
    public ResponseEntity<CategoryResponse> createCategory(
            @Parameter(description = "Category information to create", required = true)
            @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse savedCategory = categoryService.save(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update category information",
            description = "Updates information of an existing category"
    )
    public ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = "ID of the category to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated information", required = true)
            @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse updatedCategory = categoryService.update(id, categoryRequest);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete category",
            description = "Deletes a category from the system"
    )
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID of the category to delete", required = true)
            @PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

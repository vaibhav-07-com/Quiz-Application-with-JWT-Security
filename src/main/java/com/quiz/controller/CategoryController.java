package com.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quiz.dto.CategoryRequestDto;
import com.quiz.dto.CategoryResponseDto;
import com.quiz.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all categories
    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Add a new category
    @PostMapping
    public ResponseEntity<CategoryResponseDto> addCategory(
            @Valid @RequestBody CategoryRequestDto dto) {
        CategoryResponseDto saved = categoryService.addCategory(dto);
        return ResponseEntity.status(201).body(saved);
    }

    // Delete category by name
    @DeleteMapping
    public ResponseEntity<Void> deleteCategory(@RequestParam String name) {
        categoryService.deleteCategory(name);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
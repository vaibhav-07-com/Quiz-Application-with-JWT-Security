package com.quiz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.dto.CategoryRequestDto;
import com.quiz.dto.CategoryResponseDto;
import com.quiz.model.Category;
import com.quiz.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;

    @Autowired
    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // Add a new category
    public CategoryResponseDto addCategory(CategoryRequestDto dto) {

        // Check if category with same name exists
        categoryRepo.findByCName(dto.getCName()).ifPresent(c -> {
            throw new RuntimeException("Category already exists with name: " + dto.getCName());
        });

        Category category = new Category();
        category.setCName(dto.getCName());

        Category saved = categoryRepo.save(category);

        return mapToResponse(saved);
    }

    // Get all categories
    public List<CategoryResponseDto> getAllCategories() {
        List<CategoryResponseDto> response = new ArrayList<>();
        List<Category> categories = (List<Category>) categoryRepo.findAll();

        for (Category c : categories) {
            response.add(mapToResponse(c));
        }

        return response;
    }

    // Delete category by name
    public void deleteCategory(String name) {
        Category category = categoryRepo.findByCName(name)
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + name));
        categoryRepo.delete(category);
    }

    // Helper method for mapping
    private CategoryResponseDto mapToResponse(Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setCid(category.getCid());
        dto.setCName(category.getCName());
        return dto;
    }
}
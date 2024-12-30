package com.flaxishop.flexishop.business.service;

import com.flaxishop.flexishop.business.entity.Category;
import com.flaxishop.flexishop.business.entity.Order;
import com.flaxishop.flexishop.business.repository.CategoryRepository;
import com.flaxishop.flexishop.presentation.dto.CategoryDTO;
import com.flaxishop.flexishop.presentation.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Get all categories
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }

    // Get a category by ID
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return CategoryMapper.toCategoryDTO(category);
    }

    // Create a new category
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toCategoryEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toCategoryDTO(savedCategory);
    }

    // Update an existing category
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        existingCategory.setName(categoryDTO.getName());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.toCategoryDTO(updatedCategory);
    }

    // Delete a category by ID
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    public Category getByUuid(String uuid) {
        return categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Category not found with UUID: " + uuid));
    }
}

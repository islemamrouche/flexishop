package com.flaxishop.flexishop.business.service;

import com.flaxishop.flexishop.business.entity.Category;
import com.flaxishop.flexishop.business.repository.CategoryRepository;
import com.flaxishop.flexishop.config.data.exceptions.EntityNotFoundException;
import com.flaxishop.flexishop.presentation.dto.CategoryDTO;
import com.flaxishop.flexishop.presentation.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Create a new category
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toDTO(savedCategory);
    }

    // Get a category by its ID
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + categoryId));
        return CategoryMapper.toDTO(category);
    }

    // Get a category by its UUID
    public CategoryDTO getCategoryByUuid(String uuid) {
        Category category = categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with UUID: " + uuid));
        return CategoryMapper.toDTO(category);
    }

    // Get all categories
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Update an existing category
    @Transactional
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + categoryId));

        existingCategory.setName(categoryDTO.getName());
        existingCategory.setUuid(categoryDTO.getUuid());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.toDTO(updatedCategory);
    }

    // Delete a category by its ID
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + categoryId));
        categoryRepository.delete(category);
    }
}

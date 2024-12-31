package com.flaxishop.flexishop.presentation.mapper;

import com.flaxishop.flexishop.business.entity.Category;
import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.presentation.dto.CategoryDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    // Method to convert Category entity to CategoryDTO
    public static CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        // Convert associated Product entities to their UUIDs
        List<String> productUUIDList = category.getProducts().stream()
                .map(Product::getUuid)
                .collect(Collectors.toList());

        return new CategoryDTO(
                category.getId(),
                category.getUuid(),
                category.getName(),
                productUUIDList
        );
    }

    // Method to convert CategoryDTO back to Category entity
    public static Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }

        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setUuid(categoryDTO.getUuid());
        category.setName(categoryDTO.getName());

        // If necessary, logic to fetch or map Product entities by their UUIDs can be added here.
        // This is left as a placeholder for now, as it depends on your application's needs.

        return category;
    }
}

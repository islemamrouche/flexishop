package com.flaxishop.flexishop.presentation.mapper;

import com.flaxishop.flexishop.business.entity.Category;
import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.presentation.dto.CategoryDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    // Method to convert Category entity to CategoryDTO
    public static CategoryDTO toCategoryDTO(Category category) {
        if (category == null) {
            return null;
        }

        List<String> productUUIDList = category.getProducts().stream()
                .map(Product::getUuid) // Get the UUID of each product
                .collect(Collectors.toList());

        return new CategoryDTO(
                category.getId(),
                category.getUuid(),
                category.getName(),
                productUUIDList
        );
    }

    // Method to convert CategoryDTO back to Category entity
    public static Category toCategoryEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }

        // If needed, you can implement logic to map productUUIDList to actual Product entities.
        // However, since this is a minimal representation, we'll just leave it as a placeholder.

        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setUuid(categoryDTO.getUuid());
        category.setName(categoryDTO.getName());
        // productUUIDList is not being used in this case to set products, but you can add that logic if required

        return category;
    }
}

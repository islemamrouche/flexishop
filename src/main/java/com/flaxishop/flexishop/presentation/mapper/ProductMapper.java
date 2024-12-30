package com.flaxishop.flexishop.presentation.mapper;

import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.business.entity.Store;
import com.flaxishop.flexishop.presentation.dto.ProductDTO;

public class ProductMapper {

    // Method to convert Product entity to ProductDTO
    public static ProductDTO toProductDTO(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductDTO(
                product.getId(),
                product.getUuid(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStore() != null ? product.getStore().getUuid() : null // Simplified store reference
        );
    }

    // Method to convert ProductDTO back to Product entity
    public static Product toProductEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setUuid(productDTO.getUuid());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        // Handle store (assumed you can fetch the store by UUID or set it directly)
        if (productDTO.getStoreUUID() != null) {
            Store store = new Store();
            store.setUuid(productDTO.getStoreUUID());  // Assumed that store can be fetched by UUID
            product.setStore(store);
        }

        return product;
    }
}

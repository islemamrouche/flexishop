package com.flaxishop.flexishop.presentation.mapper;

import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.business.entity.Store;
import com.flaxishop.flexishop.business.entity.User;
import com.flaxishop.flexishop.presentation.dto.StoreDTO;

import java.util.List;
import java.util.stream.Collectors;

public class StoreMapper {

    // Method to convert Store entity to StoreDTO
    public static StoreDTO toDTO(Store store) {
        if (store == null) {
            return null;
        }

        // Map the list of Product entities to a list of UUIDs
        List<String> productUUIDList = store.getProducts().stream()
                .map(Product::getUuid) // Extract UUID from each Product entity
                .collect(Collectors.toList());

        return new StoreDTO(
                store.getId(),
                store.getUuid(),
                store.getName(),
                store.getStoreUrl(),
                store.getOwner() != null ? store.getOwner().getId() : null, // Extracting the owner ID
                productUUIDList
        );
    }

    // Method to convert StoreDTO back to Store entity
    public static Store toEntity(StoreDTO storeDTO) {
        if (storeDTO == null) {
            return null;
        }

        Store store = new Store();
        store.setId(storeDTO.getId());
        store.setUuid(storeDTO.getUuid());
        store.setName(storeDTO.getName());
        store.setStoreUrl(storeDTO.getStoreUrl());

        // Map ownerId to a minimal User entity (fetch the full entity if necessary)
        if (storeDTO.getOwnerId() != null) {
            // You will need to implement logic to fetch the User entity by ID
            // For now, creating a minimal User object with only the ID set
            User owner = new User();
            owner.setId(storeDTO.getOwnerId());
            store.setOwner(owner);
        }

        // Mapping productUUIDList to Product entities (fetch these entities as needed)
        // This is a placeholder for actual fetching logic
        if (storeDTO.getProductUUIDList() != null) {
            List<Product> products = storeDTO.getProductUUIDList().stream()
                    .map(uuid -> {
                        Product product = new Product();
                        product.setUuid(uuid);
                        return product;
                    })
                    .collect(Collectors.toList());
            store.setProducts(products);
        }

        return store;
    }
}

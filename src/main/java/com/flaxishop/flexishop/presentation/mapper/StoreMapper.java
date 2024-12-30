package com.flaxishop.flexishop.presentation.mapper;

import com.flaxishop.flexishop.business.entity.Store;
import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.presentation.dto.StoreDTO;

import java.util.List;
import java.util.stream.Collectors;

public class StoreMapper {

    // Method to convert Store entity to StoreDTO
    public static StoreDTO toStoreDTO(Store store) {
        if (store == null) {
            return null;
        }

        // Map product UUIDs from products associated with the store
        List<String> productUUIDList = store.getProducts() != null
                ? store.getProducts().stream()
                .map(Product::getUuid)
                .collect(Collectors.toList())
                : null;

        return new StoreDTO(
                store.getId(),
                store.getUuid(),
                store.getName(),
                store.getStoreUrl(),
                store.getOwner() != null ? store.getOwner().getId() : null,  // Owner ID simplified
                productUUIDList
        );
    }

    // Method to convert StoreDTO back to Store entity
    public static Store toStoreEntity(StoreDTO storeDTO) {
        if (storeDTO == null) {
            return null;
        }

        Store store = new Store();
        store.setId(storeDTO.getId());
        store.setUuid(storeDTO.getUuid());
        store.setName(storeDTO.getName());
        store.setStoreUrl(storeDTO.getStoreUrl());

        // Set the owner and product list if needed
        // Assuming the owner (User) can be set by fetching the User entity based on ownerId
        if (storeDTO.getOwnerId() != null) {
            // Fetch User entity by ownerId and set it to store.setOwner(user)
            // User user = userService.findById(storeDTO.getOwnerId());
            // store.setOwner(user);
        }

        // No need to map the products list from the DTO, as it only contains UUIDs
        // The product association will be handled separately when needed

        return store;
    }
}

package com.flaxishop.flexishop.business.service;

import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.business.entity.Store;
import com.flaxishop.flexishop.business.entity.User;
import com.flaxishop.flexishop.business.repository.StoreRepository;
import com.flaxishop.flexishop.presentation.dto.StoreDTO;
import com.flaxishop.flexishop.presentation.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    // Create a new store
    public StoreDTO createStore(StoreDTO storeDTO) {
        Store store = StoreMapper.toEntity(storeDTO);
        Store savedStore = storeRepository.save(store);
        return StoreMapper.toDTO(savedStore);
    }

    // Get all stores
    public List<StoreDTO> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream()
                .map(StoreMapper::toDTO)
                .toList();
    }

    // Get a specific store by ID
    public StoreDTO getStoreById(Long id) {
        Optional<Store> storeOptional = storeRepository.findById(id);
        return storeOptional.map(StoreMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + id));
    }

    // Update a store
    public StoreDTO updateStore(Long id, StoreDTO storeDTO) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + id));

        store.setName(storeDTO.getName());
        store.setStoreUrl(storeDTO.getStoreUrl());

        // If you need to update the owner or products, handle them here
        if (storeDTO.getOwnerId() != null) {
            User owner = new User();
            owner.setId(storeDTO.getOwnerId());
            store.setOwner(owner);
        }

        // Update products list if necessary
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

        Store updatedStore = storeRepository.save(store);
        return StoreMapper.toDTO(updatedStore);
    }

    // Delete a store
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}

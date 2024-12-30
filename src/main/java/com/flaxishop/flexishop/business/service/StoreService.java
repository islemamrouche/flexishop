package com.flaxishop.flexishop.business.service;

import com.flaxishop.flexishop.business.entity.Category;
import com.flaxishop.flexishop.business.entity.Store;
import com.flaxishop.flexishop.business.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + id));
    }

    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    public Store updateStore(Long id, Store storeDetails) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + id));
        store.setName(storeDetails.getName());
//        store.setBaseUrl(storeDetails.getBaseUrl());
        // Update other fields as necessary
        return storeRepository.save(store);
    }

    public void deleteStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + id));
        storeRepository.delete(store);
    }

    public Store getByUuid(String uuid) {
        return storeRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Store not found with UUID: " + uuid));
    }
}

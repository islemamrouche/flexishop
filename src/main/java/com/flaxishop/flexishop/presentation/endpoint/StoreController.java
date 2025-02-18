package com.flaxishop.flexishop.presentation.endpoint;

import com.flaxishop.flexishop.business.service.StoreService;
import com.flaxishop.flexishop.presentation.dto.StoreDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    // Constructor injection for the service
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // Get all stores
    @GetMapping
    public ResponseEntity<List<StoreDTO>> getAllStores() {
        List<StoreDTO> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    // Get a specific store by its ID
    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getStoreById(@PathVariable Long id) {
        try {
            StoreDTO storeDTO = storeService.getStoreById(id);
            return ResponseEntity.ok(storeDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new store
    @PostMapping
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
        try {
            StoreDTO createdStore = storeService.createStore(storeDTO);
            return ResponseEntity.status(201).body(createdStore);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/{id}/upload-logo")
    public ResponseEntity<String> uploadLogo(@PathVariable Long id, @RequestParam MultipartFile file) {
        try {
            storeService.uploadLogo(id, file);
            return ResponseEntity.ok("Logo uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload logo: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/logo")
    public ResponseEntity<byte[]> getLogo(@PathVariable Long id) {
        try {
            byte[] logo = storeService.getLogo(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(logo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    // Update an existing store
    @PutMapping("/{id}")
    public ResponseEntity<StoreDTO> updateStore(@PathVariable Long id, @RequestBody StoreDTO storeDTO) {
        try {
            StoreDTO updatedStore = storeService.updateStore(id, storeDTO);
            return ResponseEntity.ok(updatedStore);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a store by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        try {
            storeService.deleteStore(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.flaxishop.flexishop.business.service;

import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.business.entity.Store;
import com.flaxishop.flexishop.business.entity.User;
import com.flaxishop.flexishop.business.repository.StoreRepository;
import com.flaxishop.flexishop.presentation.dto.StoreDTO;
import com.flaxishop.flexishop.presentation.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final String logoDirectory;


    public StoreService(StoreRepository storeRepository, @Value("${store.logo.directory}") String logoDirectory) {
        this.storeRepository = storeRepository;
        this.logoDirectory = logoDirectory;
    } // Change to your directory


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

    public void uploadLogo(Long id, MultipartFile file) throws IOException {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));

        // Validate file type (allowing jpg, jpeg, png)
        String contentType = file.getContentType();
        if (contentType != null && !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Invalid file type. Only images are allowed.");
        }

        // Ensure file has a valid image extension (jpg, jpeg, png)
        String filename = file.getOriginalFilename();
        if (filename != null && !(filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png"))) {
            throw new IllegalArgumentException("Invalid file format. Only JPG, JPEG, or PNG files are allowed.");
        }

        // Generate a unique filename with current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM_dd_yyyy_HH_mm_ss");
        String timestamp = LocalDateTime.now().format(formatter);
        String uniqueFilename = timestamp + "_" + filename;

        Path filePath = Paths.get(logoDirectory, uniqueFilename);

        // Save the file to the directory
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        // Save the file path in the database
        store.setLogoPath(filePath.toString());
        storeRepository.save(store);
    }


    public byte[] getLogo(Long id) throws IOException {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));

        // Read the file from the stored path
        Path filePath = Paths.get(store.getLogoPath());
        if (!Files.exists(filePath)) {
            throw new RuntimeException("Logo file not found.");
        }
        return Files.readAllBytes(filePath);
    }


    // Delete a store
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}

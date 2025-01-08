package com.flaxishop.flexishop.business.service;

import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.business.entity.Store;
import com.flaxishop.flexishop.business.entity.User;
import com.flaxishop.flexishop.business.repository.StoreRepository;
import com.flaxishop.flexishop.presentation.dto.StoreDTO;
import com.flaxishop.flexishop.presentation.mapper.StoreMapper;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final MinioClient minioClient;
    private final String bucketName; // Name of the bucket where logos will be stored


    public StoreService(StoreRepository storeRepository, MinioClient minioClient,
                        @Value("${minio.bucket-name}") String bucketName) {
        this.storeRepository = storeRepository;
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

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

    // Upload the logo to MinIO
    public void uploadLogo(Long id, MultipartFile file) throws IOException, MinioException {
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

        try {
            // Use MinIO to upload the file
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(uniqueFilename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload logo: I/O error occurred while reading the file.", e);
        } catch (MinioException e) {
            throw new RuntimeException("Failed to upload logo: MinIO-specific error occurred.", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to upload logo: No such algorithm error occurred when processing the file.", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Failed to upload logo: Invalid key error occurred while connecting to MinIO.", e);
        }


        // Save the file path (which is the filename in MinIO) in the database
        store.setLogoPath(uniqueFilename);  // You can store just the filename or full MinIO URL
        storeRepository.save(store);
    }


    // Retrieve the logo from MinIO
    public byte[] getLogo(Long id) throws IOException, MinioException {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));

        // Get the file from MinIO
        String filename = store.getLogoPath();
        if (filename == null) {
            throw new RuntimeException("Logo not found for the store.");
        }

        // Download the file from MinIO
        try {
            // Use MinIO to get the object
            GetObjectResponse response = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .build()
            );

            // Now you can work with the response's input stream
            InputStream inputStream = response;
            byte[] data = inputStream.readAllBytes();
            return data;
        } catch (MinioException e) {
            throw new RuntimeException("Failed to retrieve object from MinIO.", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read object data.", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }

    // Delete a store
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}

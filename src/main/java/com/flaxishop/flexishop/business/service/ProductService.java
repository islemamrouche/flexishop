package com.flaxishop.flexishop.business.service;

import com.flaxishop.flexishop.business.entity.Category;
import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.business.entity.Store;
import com.flaxishop.flexishop.business.repository.ProductRepository;
import com.flaxishop.flexishop.presentation.dto.ProductDTO;
import com.flaxishop.flexishop.presentation.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Create a new product
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = ProductMapper.toProductEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toProductDTO(savedProduct);
    }

    // Get a product by its UUID
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.toProductDTO(product);
    }

    // Update an existing product
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        // Update the entity with values from DTO
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setUuid(productDTO.getUuid());
        // Update the store reference if needed (or fetch the store by UUID)
        if (productDTO.getStoreUUID() != null) {
            Store store = new Store();
            store.setUuid(productDTO.getStoreUUID());
            existingProduct.setStore(store);
        }
        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.toProductDTO(updatedProduct);
    }

    // Delete a product by its ID
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    // Get all products
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::toProductDTO).collect(Collectors.toList());
    }

    public Product getByUuid(String uuid) {
        return productRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Product not found with UUID: " + uuid));
    }
}

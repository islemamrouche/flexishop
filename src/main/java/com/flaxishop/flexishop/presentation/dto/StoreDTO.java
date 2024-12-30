package com.flaxishop.flexishop.presentation.dto;

import lombok.Data;

import java.util.List;

@Data
public class StoreDTO {
    private Long id;
    private String uuid;
    private String name;
    private String storeUrl;
    private Long ownerId; // Simplified reference to the owner (User)
    private List<Long> productIds; // Simplified list of associated product IDs
}

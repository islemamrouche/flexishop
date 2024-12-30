package com.flaxishop.flexishop.presentation.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String uuid;
    private String name;
    private String description;
    private Double price;
    // Add more fields if applicable
}

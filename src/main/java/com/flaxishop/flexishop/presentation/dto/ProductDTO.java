package com.flaxishop.flexishop.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Long id;
    private String uuid;
    private String name;
    private String description;
    private BigDecimal price;
    private String storeUUID;

    public void setCategoryId(Long aLong) {
    }
    // Add more fields if applicable
}

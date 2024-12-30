package com.flaxishop.flexishop.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreDTO {
    private Long id;
    private String uuid;
    private String name;
    private String storeUrl;
    private Long ownerId; // Simplified reference to the owner (User)
    private List<String> productUUIDList; // Simplified list of associated product IDs
}

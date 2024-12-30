package com.flaxishop.flexishop.presentation.dto;

import com.flaxishop.flexishop.presentation.dto.ProductDTO;
import lombok.Data;
import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String uuid;
    private String name;

    // Minimal representation of associated products
    private List<ProductDTO> products;
}

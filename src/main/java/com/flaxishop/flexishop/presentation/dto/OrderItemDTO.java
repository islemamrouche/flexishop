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
public class OrderItemDTO {
    private Long id;
    private String uuid;
    private String orderUUID; // Simplified order reference
    private String productUUID; // Simplified product reference
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}

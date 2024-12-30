package com.flaxishop.flexishop.presentation.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long id;
    private String uuid;
    private Long orderId; // Simplified order reference
    private Long productId; // Simplified product reference
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}

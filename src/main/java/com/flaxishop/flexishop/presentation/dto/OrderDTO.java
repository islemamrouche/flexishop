package com.flaxishop.flexishop.presentation.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDTO {
    private Long id;
    private String uuid;
    private Long userId; // Simplified user reference
    private Long storeId; // Simplified store reference
    private Date orderDate;
    private BigDecimal totalAmount;
    private String status;
}

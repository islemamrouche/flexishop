package com.flaxishop.flexishop.presentation.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentDTO {
    private Long id;
    private String uuid;
    private Long orderId; // Simplified reference to the associated order
    private Date paymentDate;
    private BigDecimal amount;
    private String paymentMethod;
    private String status; // Payment status (e.g., "PENDING", "COMPLETED")
}

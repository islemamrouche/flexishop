package com.flaxishop.flexishop.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO {
    private Long id;
    private String uuid;
    private Long orderId; // Simplified reference to the associated order
    private Date paymentDate;
    private BigDecimal amount;
    private String paymentUUID;
    private String status; // Payment status (e.g., "PENDING", "COMPLETED")
}

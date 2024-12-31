package com.flaxishop.flexishop.presentation.mapper;

import com.flaxishop.flexishop.business.entity.Order;
import com.flaxishop.flexishop.business.entity.Payment;
import com.flaxishop.flexishop.presentation.dto.PaymentDTO;

public class PaymentMapper {

    // Method to convert Payment entity to PaymentDTO
    public static PaymentDTO toDTO(Payment payment) {
        if (payment == null) {
            return null;
        }

        return new PaymentDTO(
                payment.getId(),
                payment.getUuid(),
                payment.getOrder() != null ? payment.getOrder().getId() : null, // Extracting the associated order ID
                payment.getPaymentDate(),
                payment.getAmount(),
                payment.getUuid(),
                payment.getStatus()
        );
    }

    // Method to convert PaymentDTO back to Payment entity
    public static Payment toEntity(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }

        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setUuid(paymentDTO.getUuid());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setAmount(paymentDTO.getAmount());
        payment.setUuid(paymentDTO.getPaymentUUID());
        payment.setStatus(paymentDTO.getStatus());

        // Map the orderId to an Order entity (fetch the full entity if necessary)
        if (paymentDTO.getOrderId() != null) {
            Order order = new Order();
            order.setId(paymentDTO.getOrderId());
            payment.setOrder(order);
        }

        return payment;
    }
}

package com.flaxishop.flexishop.presentation.mapper;

import com.flaxishop.flexishop.business.entity.Payment;
import com.flaxishop.flexishop.business.entity.Order;
import com.flaxishop.flexishop.presentation.dto.PaymentDTO;

public class PaymentMapper {

    // Method to convert Payment entity to PaymentDTO
    public static PaymentDTO toPaymentDTO(Payment payment) {
        if (payment == null) {
            return null;
        }

        return new PaymentDTO(
                payment.getId(),
                payment.getUuid(),
                payment.getOrder() != null ? payment.getOrder().getId() : null, // Extracting the simplified order ID
                payment.getPaymentDate(),
                payment.getAmount(),
                payment.getUuid(),  // Payment UUID is mapped directly
                payment.getStatus()
        );
    }

    // Method to convert PaymentDTO back to Payment entity
    public static Payment toPaymentEntity(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }

        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setUuid(paymentDTO.getUuid());
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setStatus(paymentDTO.getStatus());

        // Map the orderId to the actual Order entity (you will need to implement logic for fetching it)
        Order order = new Order();  // You will need to fetch the order by ID
        order.setId(paymentDTO.getOrderId());
        payment.setOrder(order);

        return payment;
    }
}

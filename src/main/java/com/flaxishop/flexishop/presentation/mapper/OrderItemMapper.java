package com.flaxishop.flexishop.presentation.mapper;

import com.flaxishop.flexishop.business.entity.Order;
import com.flaxishop.flexishop.business.entity.OrderItem;
import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.presentation.dto.OrderItemDTO;

public class OrderItemMapper {

    // Method to convert OrderItem entity to OrderItemDTO
    public static OrderItemDTO toDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getUuid(),
                orderItem.getOrder() != null ? orderItem.getOrder().getUuid() : null, // Extracting Order UUID
                orderItem.getProduct() != null ? orderItem.getProduct().getUuid() : null, // Extracting Product UUID
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getTotalPrice()
        );
    }

    // Method to convert OrderItemDTO back to OrderItem entity
    public static OrderItem toEntity(OrderItemDTO orderItemDTO) {
        if (orderItemDTO == null) {
            return null;
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDTO.getId());
        orderItem.setUuid(orderItemDTO.getUuid());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());
        orderItem.setTotalPrice(orderItemDTO.getTotalPrice());

        // Map the orderUUID and productUUID to actual entities (fetch entities via service if needed)
        if (orderItemDTO.getOrderUUID() != null) {
            Order order = new Order();
            order.setUuid(orderItemDTO.getOrderUUID());
            orderItem.setOrder(order);
        }

        if (orderItemDTO.getProductUUID() != null) {
            Product product = new Product();
            product.setUuid(orderItemDTO.getProductUUID());
            orderItem.setProduct(product);
        }

        return orderItem;
    }
}

package com.flaxishop.flexishop.presentation.mapper;

import com.flaxishop.flexishop.business.entity.OrderItem;
import com.flaxishop.flexishop.business.entity.Order;
import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.business.service.OrderService;
import com.flaxishop.flexishop.business.service.ProductService;
import com.flaxishop.flexishop.presentation.dto.OrderItemDTO;

public class OrderItemMapper {

    private final OrderService orderService;
    private final ProductService productService;

    // Constructor to inject service dependencies
    public OrderItemMapper(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    // Method to convert OrderItem entity to OrderItemDTO
    public OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getUuid(),
                orderItem.getOrder() != null ? orderItem.getOrder().getUuid() : null,  // Extracting the simplified order UUID
                orderItem.getProduct() != null ? orderItem.getProduct().getUuid() : null,  // Extracting the simplified product UUID
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getTotalPrice()
        );
    }

    // Method to convert OrderItemDTO back to OrderItem entity
    public OrderItem toOrderItemEntity(OrderItemDTO orderItemDTO) {
        if (orderItemDTO == null) {
            return null;
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDTO.getId());
        orderItem.setUuid(orderItemDTO.getUuid());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());
        orderItem.setTotalPrice(orderItemDTO.getTotalPrice());

        // Fetch the Order entity using the UUID from the DTO
        Order order = orderService.getByUuid(orderItemDTO.getOrderUUID()); // Fetch the order by UUID
        orderItem.setOrder(order);

        // Fetch the Product entity using the UUID from the DTO
        Product product = productService.getByUuid(orderItemDTO.getProductUUID()); // Fetch the product by UUID
        orderItem.setProduct(product);

        return orderItem;
    }
}

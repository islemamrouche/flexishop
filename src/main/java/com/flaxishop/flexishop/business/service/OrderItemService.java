package com.flaxishop.flexishop.business.service;

import com.flaxishop.flexishop.business.entity.Order;
import com.flaxishop.flexishop.business.entity.OrderItem;
import com.flaxishop.flexishop.business.entity.Product;
import com.flaxishop.flexishop.business.repository.OrderItemRepository;
import com.flaxishop.flexishop.presentation.dto.OrderItemDTO;
import com.flaxishop.flexishop.presentation.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Create a new order item
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = OrderItemMapper.toEntity(orderItemDTO);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return OrderItemMapper.toDTO(savedOrderItem);
    }

    // Get all order items
    public List<OrderItemDTO> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream()
                .map(OrderItemMapper::toDTO)
                .toList();
    }

    // Get a specific order item by ID
    public OrderItemDTO getOrderItemById(Long id) {
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(id);
        return orderItemOptional.map(OrderItemMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with ID: " + id));
    }

    // Update an order item
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with ID: " + id));

        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());
        orderItem.setTotalPrice(orderItemDTO.getTotalPrice());

        // If you need to update related entities like Order and Product, you can implement that logic here.
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

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return OrderItemMapper.toDTO(updatedOrderItem);
    }

    // Delete an order item
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}

package com.flaxishop.flexishop.presentation.mapper;

import com.flaxishop.flexishop.business.entity.Order;
import com.flaxishop.flexishop.business.entity.Store;
import com.flaxishop.flexishop.business.entity.User;
import com.flaxishop.flexishop.presentation.dto.OrderDTO;

public class OrderMapper {

    // Method to convert Order entity to OrderDTO
    public static OrderDTO toOrderDTO(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderDTO(
                order.getId(),
                order.getUuid(),
                order.getUser() != null ? order.getUser().getId() : null, // Extract User ID
                order.getStore() != null ? order.getStore().getId() : null, // Extract Store ID
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus()
        );
    }

    // Method to convert OrderDTO back to Order entity
    public static Order toOrderEntity(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }

        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setUuid(orderDTO.getUuid());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.getStatus());

        // Map userId and storeId to actual entities (if necessary, fetch them via service)
        if (orderDTO.getUserId() != null) {
            User user = new User();
            user.setId(orderDTO.getUserId());
            order.setUser(user);
        }

        if (orderDTO.getStoreId() != null) {
            Store store = new Store();
            store.setId(orderDTO.getStoreId());
            order.setStore(store);
        }

        return order;
    }
}

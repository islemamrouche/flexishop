package com.flaxishop.flexishop.presentation.mapper;

import com.flaxishop.flexishop.business.entity.User;
import com.flaxishop.flexishop.business.entity.Store;
import com.flaxishop.flexishop.business.entity.Order;
import com.flaxishop.flexishop.presentation.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    // Method to convert User entity to UserDTO
    public static UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        // Map store UUIDs from the user's associated stores
        List<String> storeUUIDsList = user.getStores() != null
                ? user.getStores().stream()
                .map(Store::getUuid)
                .collect(Collectors.toList())
                : null;

        // Map order UUIDs from the user's associated orders
        List<String> orderUUIDsList = user.getOrders() != null
                ? user.getOrders().stream()
                .map(Order::getUuid)
                .collect(Collectors.toList())
                : null;

        return new UserDTO(
                user.getId(),
                user.getUuid(),
                user.getUsername(),
                user.getEmail(),
                storeUUIDsList,
                orderUUIDsList
        );
    }

    // Method to convert UserDTO back to User entity
    public static User toUserEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setUuid(userDTO.getUuid());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Handle stores and orders if needed
        // For example, set stores or orders by fetching them using their UUIDs
        // If you want to map back, you will need a method to find the stores and orders by UUID.
        // user.setStores(...);
        // user.setOrders(...);

        return user;
    }
}

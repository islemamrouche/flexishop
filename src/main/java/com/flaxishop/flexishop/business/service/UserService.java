package com.flaxishop.flexishop.business.service;

import com.flaxishop.flexishop.business.entity.User;
import com.flaxishop.flexishop.business.repository.UserRepository;
import com.flaxishop.flexishop.presentation.dto.UserDTO;
import com.flaxishop.flexishop.presentation.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    // Get all users
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    // Get a specific user by ID
    public UserDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(UserMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    // Update a user
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Handle stores and orders if needed
        // user.setStores(...);
        // user.setOrders(...);

        User updatedUser = userRepository.save(user);
        return UserMapper.toDTO(updatedUser);
    }

    // Delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

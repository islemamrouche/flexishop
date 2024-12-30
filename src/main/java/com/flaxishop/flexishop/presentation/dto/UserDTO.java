package com.flaxishop.flexishop.presentation.dto;

import com.flaxishop.flexishop.presentation.dto.OrderDTO;
import com.flaxishop.flexishop.presentation.dto.StoreDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String uuid;
    private String username;
    private String email;

    // To represent related entities like stores and orders
    private List<StoreDTO> stores;
    private List<OrderDTO> orders;
}

package com.flaxishop.flexishop.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.flaxishop.flexishop.presentation.dto.OrderDTO;
import com.flaxishop.flexishop.presentation.dto.StoreDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String uuid;
    private String username;
    private String email;

    // To represent related entities like stores and orders
    private List<String> storeUUIDsList;
    private List<String> orderUUIDsList;
}

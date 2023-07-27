package com.example.FoodDeliveryDemoApp.component.userItems.user.dto;

import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTOMapper {

    public static UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setTelephone(user.getTelephone());
        dto.setRole(user.getRole());
        return dto;
    }

    public static List<UserDTO> toDtoList(List<User> users) {
        return users.stream()
                .map(UserDTOMapper::toDto)
                .collect(Collectors.toList());
    }
}


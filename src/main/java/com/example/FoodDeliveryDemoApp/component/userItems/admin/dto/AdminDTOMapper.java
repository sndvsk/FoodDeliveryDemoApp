package com.example.FoodDeliveryDemoApp.component.userItems.admin.dto;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.domain.Admin;
import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.UserDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AdminDTOMapper {

    public static AdminDTO toDto(Admin admin) {
        AdminDTO dto = new AdminDTO();
        dto.setLevel(admin.getLevel());
        dto.setUserDTO(UserDTOMapper.toDto(admin.getUser()));
        return dto;
    }

    public static List<AdminDTO> toDtoList(List<Admin> admins) {
        return admins.stream()
                .map(AdminDTOMapper::toDto)
                .collect(Collectors.toList());
    }

}

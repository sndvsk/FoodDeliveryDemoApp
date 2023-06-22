package com.example.FoodDeliveryDemoApp.component.userItems.owner.dto;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.UserDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto.RestaurantDTOMapper;

import java.util.List;
import java.util.stream.Collectors;

public class OwnerDTOMapper {

    public static OwnerDTO toDto(Owner owner) {
        OwnerDTO dto = new OwnerDTO();
        dto.setId(owner.getId());
        dto.setApproved(owner.isApproved());
        dto.setUserDTO(UserDTOMapper.toDto(owner.getUser()));
        dto.setRestaurantDTOs(owner.getRestaurants().stream()
                .map(RestaurantDTOMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public static List<OwnerDTO> toDtoList(List<Owner> owners) {
        return owners.stream()
                .map(OwnerDTOMapper::toDto)
                .collect(Collectors.toList());
    }

}

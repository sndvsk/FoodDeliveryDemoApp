package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto.MenuDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto.MenuDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantDTOMapper {

    public static RestaurantDTO toDto(Restaurant restaurant) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setDescription(restaurant.getDescription());
        dto.setTheme(restaurant.getTheme().name());
        dto.setPhone(restaurant.getPhone());
        dto.setImage(restaurant.getImage());
        dto.setOwnerId(restaurant.getOwner().getId());
        dto.setAddress(AddressDTOMapper.toDto(restaurant.getAddress()));
        List<MenuDTO> menu = restaurant.getMenus().stream()
                .map(MenuDTOMapper::toDto)
                .collect(Collectors.toList());

        dto.setMenus(menu);

        return dto;
    }

    public static List<RestaurantDTO> toDtoList(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantDTOMapper::toDto)
                .collect(Collectors.toList());
    }

}

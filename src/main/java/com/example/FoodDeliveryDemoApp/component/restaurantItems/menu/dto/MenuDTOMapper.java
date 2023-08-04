package com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class MenuDTOMapper {

    public static MenuDTO toDto(Menu menu) {
        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setVisibility(String.valueOf(menu.isVisibility()));
        dto.setOwnerId(menu.getOwner().getId());

        if (menu.getRestaurant() != null) {
            Restaurant rest = menu.getRestaurant();
            dto.setRestaurantId(rest.getId());
            dto.setRestaurantName(rest.getName());
        }
        List<ItemDTO> items = menu.getItems().stream()
                .map(ItemDTOMapper::toDto)
                .collect(Collectors.toList());
        dto.setItems(items);

        return dto;
    }

    public static List<MenuDTO> toDtoList(List<Menu> menus) {
        return menus.stream()
                .map(MenuDTOMapper::toDto)
                .collect(Collectors.toList());
    }

}

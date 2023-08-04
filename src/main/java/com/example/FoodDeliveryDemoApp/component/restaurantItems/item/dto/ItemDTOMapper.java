package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDTOMapper {

    public static ItemDTO toDto(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setPrice(item.getPrice());
        dto.setImage(item.getImage());

        String ingredients = item.getIngredients();
        if (ingredients != null) {
            List<String> ingredientsList = Arrays.asList(ingredients.split(","));
            dto.setIngredients(ingredientsList);
        } else {
            dto.setIngredients(new ArrayList<>());
        }

        String allergens = item.getAllergens();
        if (allergens != null) {
            List<String> allergensList = Arrays.asList(allergens.split(","));
            dto.setAllergens(allergensList);
        } else {
            dto.setAllergens(new ArrayList<>());
        }

        if (item.getMenu() != null) {
            Menu menu = item.getMenu();
            dto.setMenuId(menu.getId());
            dto.setMenuName(menu.getName());
        }

        if (item.getRestaurant() != null) {
            Restaurant rest = item.getRestaurant();
            dto.setRestaurantId(rest.getId());
            dto.setRestaurantName(rest.getName());
        }
        return dto;
    }

    public static List<ItemDTO> toDtoList(List<Item> items) {
        return items.stream()
                .map(ItemDTOMapper::toDto)
                .collect(Collectors.toList());
    }

}

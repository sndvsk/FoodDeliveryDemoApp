package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;

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
        List<String> ingredientsList = Arrays.asList(ingredients.split(","));
        dto.setIngredients(ingredientsList);

        String allergens = item.getIngredients();
        List<String> allergensList = Arrays.asList(allergens.split(","));
        dto.setAllergens(allergensList);

        dto.setMenuId(item.getMenu().getId());
        return dto;
    }

    public static List<ItemDTO> toDtoList(List<Item> items) {
        return items.stream()
                .map(ItemDTOMapper::toDto)
                .collect(Collectors.toList());
    }

}

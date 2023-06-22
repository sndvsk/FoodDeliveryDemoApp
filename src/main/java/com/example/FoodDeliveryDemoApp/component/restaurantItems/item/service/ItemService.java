package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    List<ItemDTO> getAllItems();

    List<ItemDTO> getItemsFromMenu(Long menuId);

    ItemDTO getItem(Long itemId);

    ItemDTO addItem(Long restaurantId, String itemName, String itemDesc, Double itemPrice, String itemImage,
                       String itemIngredients, String itemAllergens);

    ItemDTO patchItem(Long itemId, String itemName, String itemDesc, Double itemPrice, String itemImage,
                         String itemIngredients, String itemAllergens);

    String deleteItem(Long itemId);
}

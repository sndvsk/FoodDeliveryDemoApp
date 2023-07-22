package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    List<ItemDTO> getAllItems();

    List<ItemDTO> getItemsByOwnerId(Long ownerId);

    List<ItemDTO> getItemsFromMenu(Long menuId);

    ItemDTO getItem(Long itemId);

    ItemDTO addItem(Long ownerId,
                    String itemName, String itemDesc, Double itemPrice, String itemImage,
                    String itemIngredients, String itemAllergens);

    ItemDTO addItemToMenu(Long ownerId, Long menuId, Long itemId);

    ItemDTO addItemToRestaurant(Long ownerId, Long restaurantId, Long itemId);

    ItemDTO patchItem(Long itemId, Long restaurantId, Long ownerId, String itemName, String itemDesc, Double itemPrice, String itemImage,
                         String itemIngredients, String itemAllergens);

    ItemDTO removeItemFromMenu(Long itemId, Long ownerId, Long restaurantId, Long menuId);

    ItemDTO removeItemFromRestaurant(Long itemId, Long ownerId, Long restaurantId);

    String deleteItem(Long itemId, Long ownerId);
}

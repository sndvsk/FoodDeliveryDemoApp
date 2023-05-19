package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;

import java.util.List;

public interface ItemService {

    List<Item> getAllItems();

    List<Item> getItemsFromMenu(Long menuId);

    Item addItem(String itemName, String itemDesc, Double itemPrice, String itemImage,
                       String itemIngredients, String itemAllergens
            //, Long menuId
    );

    Item patchItem(Long itemId, String itemName, String itemDesc, Double itemPrice, String itemImage,
                         String itemIngredients, String itemAllergens
            //, Long menuId
    );

    String deleteItem(Long itemId
                              //,Long restaurantId
    );

}

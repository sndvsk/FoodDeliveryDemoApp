package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.repository.ItemRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.repository.MenuRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MenuRepository menuRepository;

    public ItemServiceImpl(ItemRepository itemRepository, MenuRepository menuRepository) {
        this.itemRepository = itemRepository;
        this.menuRepository = menuRepository;
    }

    private void validateInputs() {

    }

    private void validateRequiredInputs() {

    }

    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            throw new CustomNotFoundException("No items in the database.");
        }
        return items;
    }

    public List<Item> getItemsFromMenu(Long menuId) {
        return menuRepository.findById(menuId)
                .map(Menu::getItems)
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
    }


    public Item addItem(String itemName, String itemDesc, Double itemPrice, String itemImage,
                              String itemIngredients, String itemAllergens) {
        Item item = new Item(itemName, itemDesc, itemPrice, itemImage, itemIngredients, itemAllergens);
        return itemRepository.save(item);
    }


    public Item patchItem(Long itemId, String itemName, String itemDesc, Double itemPrice, String itemImage,
                                String itemIngredients, String itemAllergens) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    if (itemName != null) {
                        item.setName(itemName);
                    }
                    if (itemDesc != null) {
                        item.setDescription(itemDesc);
                    }
                    if (itemPrice != null) {
                        item.setPrice(itemPrice);
                    }
                    if (itemImage != null) {
                        item.setImage(itemImage);
                    }
                    if (itemIngredients != null) {
                        item.setIngredients(itemIngredients);
                    }
                    if (itemAllergens != null) {
                        item.setAllergens(itemAllergens);
                    }
                    return itemRepository.save(item);
                })
                .orElseThrow(() -> new CustomNotFoundException("Item not found with id " + itemId));
    }


    public String deleteItem(Long itemId) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    itemRepository.delete(item);
                    return "Deleted item with id " + itemId;
                }).orElseThrow(() -> new CustomNotFoundException("Item not found with id " + itemId));
    }

}

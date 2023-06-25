package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.service;

import com.example.FoodDeliveryDemoApp.component.utils.OwnershipHelper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.repository.ItemRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.repository.MenuRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository.RestaurantRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public ItemServiceImpl(ItemRepository itemRepository,
                           MenuRepository menuRepository,
                           RestaurantRepository restaurantRepository) {
        this.itemRepository = itemRepository;
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    private void validateInputs() {

    }

    private void validateRequiredInputs() {

    }

    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            throw new CustomNotFoundException("No items in the database.");
        }
        return ItemDTOMapper.toDtoList(items);
    }

    public List<ItemDTO> getItemsFromMenu(Long menuId) {
        List<Item> items = menuRepository.findById(menuId)
                .map(Menu::getItems)
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
        return ItemDTOMapper.toDtoList(items);
    }

    public ItemDTO getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomNotFoundException("No item with id: " + itemId));
        return ItemDTOMapper.toDto(item);
    }

    public ItemDTO addItem(Long restaurantId, Long ownerId,
                           String itemName, String itemDesc, Double itemPrice, String itemImage,
                           String itemIngredients, String itemAllergens) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomNotFoundException("No restaurant with id: " + restaurantId));

        OwnershipHelper.validateOwner(ownerId, restaurant.getOwner().getId());

        Item item = new Item(itemName, itemDesc, itemPrice, itemImage, itemIngredients, itemAllergens, restaurant);
        itemRepository.save(item);
        return ItemDTOMapper.toDto(item);
    }

    public ItemDTO patchItem(Long itemId, Long restaurantId, Long ownerId,
                             String itemName, String itemDesc, Double itemPrice, String itemImage,
                             String itemIngredients, String itemAllergens) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    OwnershipHelper.validateRestaurant(restaurantId, item.getRestaurant().getId());
                    OwnershipHelper.validateOwner(ownerId, item.getOwner().getId());

                    Optional.ofNullable(itemName).ifPresent(item::setName);
                    Optional.ofNullable(itemDesc).ifPresent(item::setDescription);
                    Optional.ofNullable(itemPrice).ifPresent(item::setPrice);
                    Optional.ofNullable(itemImage).ifPresent(item::setImage);
                    Optional.ofNullable(itemIngredients).ifPresent(item::setIngredients);
                    Optional.ofNullable(itemAllergens).ifPresent(item::setAllergens);

                    itemRepository.save(item);

                    return ItemDTOMapper.toDto(item);
                })
                .orElseThrow(() -> new CustomNotFoundException("Item not found with id " + itemId));
    }

    public String deleteItem(Long itemId, Long ownerId) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    OwnershipHelper.validateOwner(ownerId, item.getOwner().getId());
                    itemRepository.delete(item);
                    return "Deleted item with id " + itemId;
                }).orElseThrow(() -> new CustomNotFoundException("Item not found with id " + itemId));
    }

}

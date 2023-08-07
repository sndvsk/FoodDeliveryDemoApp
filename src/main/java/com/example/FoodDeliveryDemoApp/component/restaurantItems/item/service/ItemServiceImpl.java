package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.service;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.component.utils.OwnershipHelper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.repository.ItemRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.repository.MenuRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository.RestaurantRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomAccessDeniedException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;

    public ItemServiceImpl(ItemRepository itemRepository,
                           MenuRepository menuRepository,
                           RestaurantRepository restaurantRepository, OwnerRepository ownerRepository) {
        this.itemRepository = itemRepository;
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.ownerRepository = ownerRepository;
    }

    private void validateInputs() {

    }

    private void validateRequiredInputs() {

    }

    @Transactional
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            throw new CustomNotFoundException("No items in the database.");
        }
        return ItemDTOMapper.toDtoList(items);
    }

    @Transactional
    public List<ItemDTO> getItemsByOwnerId(Long ownerId) {
        List<Item> items = ownerRepository.findById(ownerId)
                .map(Owner::getItems)
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with id " + ownerId));
        return ItemDTOMapper.toDtoList(items);
    }

    @Transactional
    public List<ItemDTO> getItemsFromMenu(Long menuId) {
        List<Item> items = menuRepository.findById(menuId)
                .map(Menu::getItems)
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
        return ItemDTOMapper.toDtoList(items);
    }

    @Transactional
    public ItemDTO getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomNotFoundException("No item with id: " + itemId));
        return ItemDTOMapper.toDto(item);
    }

    @Transactional
    public ItemDTO addItem(Long ownerId,
                           String itemName, String itemDesc, Double itemPrice, String itemImage,
                           String itemIngredients, String itemAllergens) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomNotFoundException("No owner with id: " + ownerId));

        if (!owner.isApproved())
            throw new CustomAccessDeniedException("You are not yet approved as an owner");

        Item item = new Item(itemName, itemDesc, itemPrice, itemImage, itemIngredients, itemAllergens, owner);
        itemRepository.save(item);
        return ItemDTOMapper.toDto(item);
    }

    @Transactional
    public ItemDTO addItemToMenu(Long ownerId, Long menuId, Long itemId) {
        ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomNotFoundException("No owner with id: " + ownerId));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomNotFoundException("No menu with id: " + menuId));

        OwnershipHelper.validateOwner(ownerId, menu.getOwner().getId());

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomNotFoundException("No item with id: " + itemId));

        item.setMenu(menu);
        itemRepository.save(item);
        return ItemDTOMapper.toDto(item);
    }

    @Transactional
    public ItemDTO addItemToRestaurant(Long ownerId, Long restaurantId, Long itemId) {
        ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomNotFoundException("No owner with id: " + ownerId));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomNotFoundException("No restaurant with id: " + restaurantId));

        OwnershipHelper.validateOwner(ownerId, restaurant.getOwner().getId());

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomNotFoundException("No item with id: " + itemId));

        item.setRestaurant(restaurant);
        itemRepository.save(item);
        return ItemDTOMapper.toDto(item);
    }

    @Transactional
    public ItemDTO patchItem(Long itemId, Long restaurantId, Long ownerId,
                             String itemName, String itemDesc, Double itemPrice, String itemImage,
                             String itemIngredients, String itemAllergens) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    restaurantRepository.findById(restaurantId)
                            .orElseThrow(() -> new CustomNotFoundException("No restaurant with id: " + restaurantId));

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

    @Transactional
    public ItemDTO removeItemFromMenu(Long itemId, Long ownerId, Long restaurantId, Long menuId) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    OwnershipHelper.validateOwner(ownerId, item.getOwner().getId());
                    OwnershipHelper.validateRestaurant(restaurantId, item.getRestaurant().getId());
                    OwnershipHelper.validateMenu(menuId, item.getMenu().getId());
                    item.setMenu(null);
                    itemRepository.save(item);
                    return ItemDTOMapper.toDto(item);
                }).orElseThrow(() -> new CustomNotFoundException("Item not found with id " + itemId));
    }

    @Transactional
    public ItemDTO removeItemFromRestaurant(Long itemId, Long ownerId, Long restaurantId) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    OwnershipHelper.validateOwner(ownerId, item.getOwner().getId());
                    OwnershipHelper.validateRestaurant(restaurantId, item.getRestaurant().getId());
                    item.setRestaurant(null);
                    item.setMenu(null);
                    itemRepository.save(item);
                    return ItemDTOMapper.toDto(item);
                }).orElseThrow(() -> new CustomNotFoundException("Item not found with id " + itemId));
    }

    @Transactional
    public String deleteItem(Long itemId, Long ownerId) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    OwnershipHelper.validateOwner(ownerId, item.getOwner().getId());
                    itemRepository.delete(item);
                    return "Deleted item with id " + itemId;
                }).orElseThrow(() -> new CustomNotFoundException("Item not found with id " + itemId));
    }

}

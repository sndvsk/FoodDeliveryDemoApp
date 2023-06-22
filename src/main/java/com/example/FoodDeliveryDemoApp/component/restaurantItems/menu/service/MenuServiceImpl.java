package com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.repository.ItemRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.MenuHidden;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto.MenuDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto.MenuDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.repository.MenuRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository.RestaurantRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ItemRepository itemRepository;
    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;

    public MenuServiceImpl(MenuRepository menuRepository,
                           ItemRepository itemRepository,
                           RestaurantRepository restaurantRepository,
                           OwnerRepository ownerRepository) {
        this.menuRepository = menuRepository;
        this.itemRepository = itemRepository;
        this.restaurantRepository = restaurantRepository;
        this.ownerRepository = ownerRepository;
    }

    private void validateInputs() {

    }

    private void validateRequiredInputs() {

    }

    public List<MenuDTO> getAllMenus() {
        List<Menu> listOfMenus = menuRepository.findAll();
        if (listOfMenus.isEmpty()) {
            throw new CustomNotFoundException("No items in the database.");
        }
        return MenuDTOMapper.toDtoList(listOfMenus);
    }

    public MenuDTO getMenuById(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
        return MenuDTOMapper.toDto(menu);
    }

    public List<MenuDTO> getMenusOfRestaurant(Long restaurantId) {
        List<Menu> menus = restaurantRepository.findById(restaurantId)
                .map(Restaurant::getMenus)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
        return MenuDTOMapper.toDtoList(menus);
    }

    public ItemDTO addItemToMenu(Long itemId, Long menuId, Long restaurantId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));

        return itemRepository.findById(itemId).map(item -> {
            item.setMenu(menu);
            itemRepository.save(item);
            return ItemDTOMapper.toDto(item);
        }).orElseThrow(() -> new CustomNotFoundException("Item not found with id " + itemId));
    }

    public MenuDTO addMenu(Long ownerId, String menuName) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with id " + ownerId));
        Menu menu = new Menu();
        menu.setName(menuName);
        menu.setHidden(MenuHidden.YES);
        menu.setOwner(owner);
        menuRepository.save(menu);
        return MenuDTOMapper.toDto(menu);
    }

    public MenuDTO addMenuToRestaurant(Long menuId, Long restaurantId) {

        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        Menu menu = optionalMenu
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        Restaurant restaurant = optionalRestaurant
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));

        menu.setRestaurant(restaurant);
        menuRepository.save(menu);
        return MenuDTOMapper.toDto(menu);

    }

    public MenuDTO patchMenuInRestaurant(Long menuId, String menuName, Long restaurantId) {
        return menuRepository.findById(menuId).map(menu -> {
            Long expectedRestaurantId = menu.getRestaurant().getId();
            if (!expectedRestaurantId.equals(restaurantId)) {
                throw new CustomBadRequestException(
                        String.format("Id of restaurant is wrong. Expected: %s, provided: %s",
                                expectedRestaurantId, restaurantId));
            }
            if (menuName != null) {
                menu.setName(menuName);
            }
            menuRepository.save(menu);
            return MenuDTOMapper.toDto(menu);
        }).orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
    }

    public String deleteMenuFromRestaurant(Long menuId, Long restaurantId) {
        return menuRepository.findById(menuId)
                .map(menu -> {
                    menu.setRestaurant(null);
                    menuRepository.save(menu);
                    return "Deleted menu from restaurant with id " + menuId;
                }).orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
    }

}

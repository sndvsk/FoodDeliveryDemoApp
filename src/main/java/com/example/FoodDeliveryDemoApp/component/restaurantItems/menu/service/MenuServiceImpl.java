package com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.repository.ItemRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.repository.MenuRepository;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository.RestaurantRepository;
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

    public MenuServiceImpl(MenuRepository menuRepository,
                           ItemRepository itemRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.itemRepository = itemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    private void validateInputs() {

    }

    private void validateRequiredInputs() {

    }

    public List<Menu> getAllMenus() {
        List<Menu> listOfMenus = menuRepository.findAll();
        if (listOfMenus.isEmpty()) {
            throw new CustomNotFoundException("No items in the database.");
        }
        return listOfMenus;
    }

    public Menu getMenuById(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
    }

    public List<Menu> getMenusOfRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .map(Restaurant::getMenus)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
    }

    public Item addItemToMenu(Long itemId, Long menuId, Long restaurantId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));

        return itemRepository.findById(itemId).map(item -> {
            item.setMenu(menu);
            return itemRepository.save(item);
        }).orElseThrow(() -> new CustomNotFoundException("Item not found with id " + itemId));
    }

    public Menu addMenuToRestaurant(Long menuId, Long restaurantId) {

/*        return restaurantRepository.findById(restaurantId).map(restaurant -> {
                    Menu menu = new Menu(menuName);
                    return menuRepository.save(menu);
                }).orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));*/

        Optional<Menu> optionalMenu = menuRepository.findById(menuId);
        Menu menu = optionalMenu
                .orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));

        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        Restaurant restaurant = optionalRestaurant
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));

        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);

    }

    public Menu patchMenuInRestaurant(Long menuId, String menuName, Long restaurantId) {
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
            return menuRepository.save(menu);
        }).orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
    }

    public String deleteMenuFromRestaurant(Long menuId, Long restaurantId) {
        return menuRepository.findById(menuId)
                .map(menu -> {
                    menu.setRestaurant(new Restaurant());
                    return "Deleted menu from restaurant with id " + menuId;
                }).orElseThrow(() -> new CustomNotFoundException("Menu not found with id " + menuId));
    }

}

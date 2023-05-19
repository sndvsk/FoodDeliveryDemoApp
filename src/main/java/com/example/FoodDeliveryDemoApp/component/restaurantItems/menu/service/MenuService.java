package com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getAllMenus();

    Menu getMenuById(Long menuId);

    List<Menu> getMenusOfRestaurant(Long restaurantId);

    Item addItemToMenu(Long itemId, Long menuId, Long restaurantId);

    Menu addMenuToRestaurant(Long menuId, Long restaurantId);

    Menu patchMenuInRestaurant(Long menuId, String menuName, Long restaurantId);

    String deleteMenuFromRestaurant(Long menuId, Long restaurantId);
}

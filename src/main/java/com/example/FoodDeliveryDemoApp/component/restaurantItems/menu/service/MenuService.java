package com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto.MenuDTO;

import java.util.List;

public interface MenuService {

    List<MenuDTO> getAllMenus();

    MenuDTO getMenuById(Long menuId);

    List<MenuDTO> getMenusOfRestaurant(Long restaurantId);

    MenuDTO addMenu(Long ownedId, String menuName);

    ItemDTO addItemToMenu(Long itemId, Long menuId, Long restaurantId, Long ownerId);

    MenuDTO addMenuToRestaurant(Long menuId, Long restaurantId, Long ownerId);

    MenuDTO makeMenuVisible(Long menuId, Long ownerId);

    MenuDTO patchMenuInRestaurant(Long menuId, String menuName, Long restaurantId, Long ownerId);

    String deleteMenuFromRestaurant(Long menuId, Long restaurantId, Long ownerId);

    String deleteMenu(Long menuId, Long ownerId);
}

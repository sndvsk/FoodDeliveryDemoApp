package com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto.MenuDTO;

import java.util.List;

public interface MenuService {

    List<MenuDTO> getAllMenus();

    List<MenuDTO> getMenusByOwnerId(Long ownerId);

    MenuDTO getMenuById(Long menuId);

    List<MenuDTO> getMenusOfRestaurant(Long restaurantId);

    MenuDTO addMenu(Long ownedId, String menuName);

    ItemDTO addItemToMenu(Long itemId, Long menuId, Long restaurantId, Long ownerId);

    MenuDTO addMenuToRestaurant(Long menuId, Long restaurantId, Long ownerId);

    MenuDTO toggleMenuVisibility(Long menuId, Long ownerId);

    MenuDTO patchMenu(Long menuId, String menuName, Long ownerId);

    MenuDTO removeMenuFromRestaurant(Long menuId, Long restaurantId, Long ownerId);

    String deleteMenu(Long menuId, Long ownerId);
}

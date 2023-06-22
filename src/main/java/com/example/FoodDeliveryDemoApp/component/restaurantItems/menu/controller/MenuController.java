package com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.controller;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto.MenuDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping(path = "/all")
    @Operation(summary = "Get all menus")
    public ResponseEntity<List<MenuDTO>> getAllMenus() {

        List<MenuDTO> menus = menuService.getAllMenus();

        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    // todo get menu by id
    @GetMapping(path = "/restaurant/{restaurantId}")
    @Operation(summary = "Get all menus of a restaurant")
    public ResponseEntity<List<MenuDTO>> getMenusOfRestaurant(
            @Parameter(name = "restaurantId", description = "Restaurant id", example = "1")
            @PathVariable(value = "restaurantId") Long restaurantId) {

        List<MenuDTO> menus = menuService.getMenusOfRestaurant(restaurantId);

        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @GetMapping(path = "/{menuId}")
    @Operation(summary = "Get menu")
    public ResponseEntity<MenuDTO> getMenu(
            @Parameter(name = "menuId", description = "Menu id", example = "1")
            @PathVariable(value = "menuId") Long menuId) {

        MenuDTO menu = menuService.getMenuById(menuId);

        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @PostMapping(path = "/{ownerId}")
    @Operation(summary = "Add menu (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<MenuDTO> addMenu(
            @Parameter(name = "ownerId", description = "Owner id", example = "1")
            @PathVariable(value = "ownerId") Long ownerId,
            @Parameter(name = "menuName", description = "Menu name", example = "Pizza menu")
            @RequestParam(value = "menuName") String menuName) {

        MenuDTO menu = menuService.addMenu(ownerId, menuName);

        return new ResponseEntity<>(menu, HttpStatus.CREATED);
    }

    @PostMapping(path = "/restaurant/{restaurantId}/menu/{menuId}")
    @Operation(summary = "Add item to menu (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<ItemDTO> addItemToMenu(
            @Parameter(name = "menuId", description = "Menu id", example = "1")
            @PathVariable(value = "menuId") Long menuId,
            @Parameter(name = "restaurantId", description = "Restaurant id", example = "1")
            @PathVariable(value = "restaurantId") Long restaurantId,
            @Parameter(name = "itemId", description = "Item id", example = "1")
            @RequestParam(value = "itemId") Long itemId) {
        ItemDTO item = menuService.addItemToMenu(itemId, menuId, restaurantId);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping(path = "/restaurant/{restaurantId}")
    @Operation(summary = "Add menu to restaurant (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<MenuDTO> addMenuToRestaurant(
            @Parameter(name = "menuId", description = "Menu id", example = "1")
            @RequestParam Long menuId,
            @Parameter(name = "restaurantId", description = "Restaurant id", example = "1")
            @PathVariable(value = "restaurantId") Long restaurantId) {

        MenuDTO menu = menuService.addMenuToRestaurant(menuId, restaurantId);

        return new ResponseEntity<>(menu, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/restaurant/{restaurantId}/menu/{menuId}")
    @Operation(summary = "Update menu in restaurant (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<MenuDTO> patchMenuInRestaurant(
            @Parameter(name = "menuId", description = "Menu id", example = "1")
            @PathVariable Long menuId,
            @Parameter(name = "menuName", description = "Menu name", example = "Burger menu")
            @RequestParam String menuName,
            @Parameter(name = "restaurantMenuId", description = "Restaurant menu id", example = "1")
            @PathVariable(value = "restaurantId") Long restaurantMenuId) {

        MenuDTO menu = menuService.patchMenuInRestaurant(menuId, menuName, restaurantMenuId);

        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @DeleteMapping(path = "/restaurant/{restaurantId}/menu/{menuId}")
    @Operation(summary = "Delete menu from restaurant")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<String> deleteMenuFromRestaurant(
            @Parameter(name = "menuId", description = "Menu id", example = "1")
            @PathVariable Long menuId,
            @Parameter(name = "restaurantMenuId", description = "Restaurant menu id", example = "1")
            @PathVariable(value = "restaurantId") Long restaurantId) {

        String response = menuService.deleteMenuFromRestaurant(menuId, restaurantId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // todo delete menu

}

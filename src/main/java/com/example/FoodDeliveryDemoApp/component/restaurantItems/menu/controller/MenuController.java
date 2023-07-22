package com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.controller;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;
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

    @GetMapping(path = "/owner/{ownerId}")
    @Operation(summary = "Get all menus of a restaurant")
    public ResponseEntity<List<MenuDTO>> getMenusByOwnerId(
            @Parameter(name = "ownerId", description = "Owner id", example = "1")
            @PathVariable(value = "ownerId") Long ownerId) {

        List<MenuDTO> menus = menuService.getMenusByOwnerId(ownerId);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

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

    @PostMapping(path = "/restaurant/{restaurantId}/{menuId}")
    @Operation(summary = "Add item to menu (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<ItemDTO> addItemToMenu(
            @Parameter(name = "menuId", description = "Menu id", example = "1")
            @PathVariable(value = "menuId") Long menuId,
            @Parameter(name = "restaurantId", description = "Restaurant id", example = "1")
            @PathVariable(value = "restaurantId") Long restaurantId,
            @Parameter(name = "itemId", description = "Item id", example = "1")
            @RequestParam(value = "itemId") Long itemId,
            @Parameter(name = "ownerId", description = "Owner id", example = "1")
            @RequestParam(value = "ownerId") Long ownerId) {

        ItemDTO item = menuService.addItemToMenu(itemId, menuId, restaurantId, ownerId);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping(path = "/restaurant/{restaurantId}")
    @Operation(summary = "Add menu to restaurant (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<MenuDTO> addMenuToRestaurant(
            @Parameter(name = "menuId", description = "Menu id", example = "1")
            @RequestParam Long menuId,
            @Parameter(name = "ownerId", description = "Owner id", example = "1")
            @RequestParam(value = "ownerId") Long ownerId,
            @Parameter(name = "restaurantId", description = "Restaurant id", example = "1")
            @PathVariable(value = "restaurantId") Long restaurantId) {

        MenuDTO menu = menuService.addMenuToRestaurant(menuId, restaurantId, ownerId);
        return new ResponseEntity<>(menu, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/visibility/{menuId}")
    @Operation(summary = "Delete menu from restaurant")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<MenuDTO> toggleMenuVisibility(
            @Parameter(name = "menuId", description = "Menu id", example = "1")
            @PathVariable Long menuId,
            @Parameter(name = "ownerId", description = "Owner id", example = "1")
            @RequestParam Long ownerId) {

        MenuDTO response = menuService.toggleMenuVisibility(menuId, ownerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(path = "/{menuId}")
    @Operation(summary = "Update menu in restaurant (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<MenuDTO> patchMenu(
            @Parameter(name = "menuId", description = "Menu id", example = "1")
            @PathVariable Long menuId,
            @Parameter(name = "menuName", description = "Menu name", example = "Burger menu")
            @RequestParam String menuName,
            @Parameter(name = "ownerId", description = "Owner id", example = "1")
            @RequestParam Long ownerId) {

        MenuDTO menu = menuService.patchMenu(menuId, menuName, ownerId);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @PatchMapping(path = "/restaurant/{restaurantId}/menu/{menuId}")
    @Operation(summary = "Remove menu from restaurant")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<MenuDTO> removeMenuFromRestaurant(
            @Parameter(name = "menuId", description = "Menu id", example = "1")
            @PathVariable Long menuId,
            @Parameter(name = "ownerId", description = "Owner id", example = "1")
            @RequestParam Long ownerId,
            @Parameter(name = "restaurantMenuId", description = "Restaurant menu id", example = "1")
            @PathVariable(value = "restaurantId") Long restaurantId) {

        MenuDTO menu = menuService.removeMenuFromRestaurant(menuId, restaurantId, ownerId);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{menuId}")
    @Operation(summary = "Delete menu from restaurant")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<String> deleteMenu(
            @Parameter(name = "menuId", description = "Menu id", example = "1")
            @PathVariable Long menuId,
            @Parameter(name = "ownerId", description = "Owner id", example = "1")
            @RequestParam Long ownerId) {

        String response = menuService.deleteMenu(menuId, ownerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

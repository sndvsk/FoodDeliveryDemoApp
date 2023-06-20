package com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.controller;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
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
    @Operation(summary = "")
    public ResponseEntity<?> getAllMenus() {

        List<Menu> menus = menuService.getAllMenus();

        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    // todo get menu by id
    @GetMapping(path = "/menu/{menuId}")
    @Operation(summary = "")
    public ResponseEntity<?> getMenu(
            @Parameter(name = "menuId", description = "Menu id", example = "")
            @PathVariable(value = "menuId") Long menuId) {

        Menu menu = menuService.getMenuById(menuId);

        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @GetMapping(path = "/restaurant/{restaurantId}")
    @Operation(summary = "")
    public ResponseEntity<?> getMenusOfRestaurant(
            @Parameter(name = "restaurantId", description = "Restaurant id", example = "")
            @PathVariable(value = "restaurantId") Long restaurantId) {

        List<Menu> menus = menuService.getMenusOfRestaurant(restaurantId);

        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @PostMapping(path = "/restaurant/{restaurantId}/menu/{menuId}/item/{itemId}")
    @Operation(summary = "")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<?> addItemToMenu(
            @Parameter(name = "menuId", description = "Menu id", example = "")
            @PathVariable(value = "menuId") Long menuId,
            @Parameter(name = "restaurantId", description = "Restaurant id", example = "")
            @PathVariable(value = "restaurantId") Long restaurantId,
            @Parameter(name = "itemId", description = "Item id", example = "")
            @PathVariable(value = "itemId") Long itemId) {
        Item item = menuService.addItemToMenu(itemId, menuId, restaurantId);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping(path = "/restaurant/{restaurantId}")
    @Operation(summary = "")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<?> addMenuToRestaurant(
            @Parameter(name = "menuId", description = "Menu id", example = "")
            @RequestParam Long menuId,
            @Parameter(name = "restaurantId", description = "Restaurant id", example = "")
            @PathVariable(value = "restaurantId") Long restaurantId) {

        Menu menu = menuService.addMenuToRestaurant(menuId, restaurantId);

        return new ResponseEntity<>(menu, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/restaurant/{restaurantId}/menu/{menuId}")
    @Operation(summary = "")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<?> patchMenuInRestaurant(
            @Parameter(name = "menuId", description = "Menu id", example = "")
            @PathVariable Long menuId,
            @Parameter(name = "menuName", description = "Menu name", example = "")
            @RequestParam String menuName,
            @Parameter(name = "restaurantMenuId", description = "Restaurant menu id", example = "")
            @PathVariable(value = "restaurantId") Long restaurantMenuId) {

        Menu menu = menuService.patchMenuInRestaurant(menuId, menuName, restaurantMenuId);

        return new ResponseEntity<>(menu, HttpStatus.OK);
    }

    @DeleteMapping(path = "/restaurant/{restaurantId}/menu/{menuId}")
    @Operation(summary = "")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<?> deleteMenuFromRestaurant(
            @Parameter(name = "menuId", description = "Menu id", example = "")
            @PathVariable Long menuId,
            @Parameter(name = "restaurantMenuId", description = "Restaurant menu id", example = "")
            @PathVariable(value = "restaurantId") Long restaurantId) {

        String response = menuService.deleteMenuFromRestaurant(menuId, restaurantId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

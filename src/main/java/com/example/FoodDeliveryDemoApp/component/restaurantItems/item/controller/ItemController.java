package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.controller;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(path = "/all")
    @Operation(summary = "Get all items for all restaurants")
    public ResponseEntity<List<ItemDTO>> getAllItemsForAllRestaurants() {
        List<ItemDTO> items = itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping(path = "/owner/{ownerId}")
    @Operation(summary = "Get all items by owner id")
    public ResponseEntity<List<ItemDTO>> getItemsByOwnerId(
            @Parameter(name = "ownerId", description = "Owner id", example = "1")
            @PathVariable(value = "ownerId") Long ownerId) {

        List<ItemDTO> items = itemService.getItemsByOwnerId(ownerId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping(path = "/restaurant/menu/{restaurantMenuId}")
    @Operation(summary = "Get all items from menu")
    public ResponseEntity<List<ItemDTO>> getItemsFromRestaurantMenu(
            @Parameter(name = "restaurantMenuId", description = "Restaurant menu id", example = "1")
            @PathVariable(value = "restaurantMenuId") Long restaurantMenuId) {

        List<ItemDTO> items = itemService.getItemsFromMenu(restaurantMenuId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping(path = "/{itemId}")
    @Operation(summary = "Get item")
    public ResponseEntity<ItemDTO> getItem(
            @Parameter(name = "itemId", description = "Item id", example = "1")
            @PathVariable(value = "itemId") Long itemId) {

        ItemDTO items = itemService.getItem(itemId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping(path = "/add/{ownerId}")
    @Operation(summary = "Add item (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<ItemDTO> addItem(
            @PathVariable Long ownerId,
            @RequestBody ItemDTO itemDto) {

        ItemDTO item = itemService.addItem(
                ownerId,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getPrice(),
                itemDto.getImage(),
                String.join(",", itemDto.getIngredients()),
                String.join(",", itemDto.getAllergens())
        );

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping(path = "/add/menu/{menuId}")
    @Operation(summary = "Add item (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<ItemDTO> addItemToMenu(
            @PathVariable Long menuId,
            @RequestParam Long ownerId,
            @RequestParam Long itemId) {

        ItemDTO item = itemService.addItemToMenu(ownerId, menuId, itemId);

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping(path = "/add/restaurant/{restaurantId}")
    @Operation(summary = "Add item (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<ItemDTO> addItemToRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam Long ownerId,
            @RequestParam Long itemId) {

        ItemDTO item = itemService.addItemToRestaurant(ownerId, restaurantId, itemId);

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/patch/{itemId}")
    @Operation(summary = "Patch item (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<ItemDTO> patchItem(
            @Parameter(name = "itemId", description = "Item id", example = "1")
            @PathVariable Long itemId,
            @RequestParam Long restaurantId,
            @RequestParam Long ownerId,
            @RequestBody ItemDTO itemDto) {

        ItemDTO item = itemService.patchItem(
                itemId,
                restaurantId,
                ownerId,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getPrice(),
                itemDto.getImage(),
                String.join(",", itemDto.getIngredients()),
                String.join(",", itemDto.getAllergens())
        );

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{itemId}")
    @Operation(summary = "Delete item (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<String> deleteItem(
            @Parameter(name = "itemId", description = "Item id", example = "1")
            @PathVariable Long itemId,
            @Parameter(name = "ownerId", description = "Owner id", example = "1")
            @RequestParam Long ownerId) {

        String response = itemService.deleteItem(itemId, ownerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

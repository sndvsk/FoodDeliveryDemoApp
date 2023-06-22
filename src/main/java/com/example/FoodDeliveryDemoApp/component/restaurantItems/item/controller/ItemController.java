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
            @Parameter(name = "itemId", description = "Restaurant menu id", example = "1")
            @PathVariable(value = "itemId") Long itemId) {

        ItemDTO items = itemService.getItem(itemId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping(path = "/add/restaurant/{restaurantId}")
    @Operation(summary = "Add item (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<ItemDTO> addItem(
            @Parameter(name = "restaurantId", description = "Restaurant id", example = "1")
            @PathVariable Long restaurantId,
            @Parameter(name = "itemName", description = "Item name", example = "Vodka")
            @RequestParam String itemName,
            @Parameter(name = "itemDesc", description = "Item description", example = "Spirit")
            @RequestParam String itemDesc,
            @Parameter(name = "itemPrice", description = "Item price", example = "10")
            @RequestParam Double itemPrice,
            @Parameter(name = "itemImage", description = "Item image", example = "")
            @RequestParam String itemImage,
            @Parameter(name = "itemIngredients", description = "Item ingredients", example = "vodka")
            @RequestParam String itemIngredients,
            @Parameter(name = "itemAllergens", description = "Item allergens", example = "alcohol")
            @RequestParam String itemAllergens) {

        ItemDTO item = itemService.addItem(restaurantId, itemName, itemDesc, itemPrice, itemImage,
                itemIngredients, itemAllergens);

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{itemId}")
    @Operation(summary = "Patch item (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<ItemDTO> patchItem(
            @Parameter(name = "itemId", description = "Item id", example = "1")
            @PathVariable Long itemId,
            @Parameter(name = "itemName", description = "Item name", example = "Vodka")
            @RequestParam(required = false) String itemName,
            @Parameter(name = "itemDesc", description = "Item description", example = "Spirit")
            @RequestParam(required = false) String itemDesc,
            @Parameter(name = "itemPrice", description = "Item price", example = "15")
            @RequestParam(required = false) Double itemPrice,
            @Parameter(name = "itemImage", description = "Item image", example = "")
            @RequestParam(required = false) String itemImage,
            @Parameter(name = "itemIngredients", description = "Item ingredients", example = "vodka")
            @RequestParam(required = false) String itemIngredients,
            @Parameter(name = "itemAllergens", description = "Item allergens", example = "alcohol")
            @RequestParam(required = false) String itemAllergens) {

        ItemDTO item = itemService.patchItem(itemId, itemName, itemDesc, itemPrice, itemImage,
                itemIngredients, itemAllergens);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{itemId}")
    @Operation(summary = "Delete item (only admin and owner)")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    public ResponseEntity<String> deleteItem(
            @Parameter(name = "itemId", description = "Item id", example = "1")
            @PathVariable Long itemId) {

        String response = itemService.deleteItem(itemId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

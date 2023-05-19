package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.controller;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(path = "/all")
    @Operation(summary = "")
    public ResponseEntity<?> getAllItemsForAllRestaurants() {
        List<Item> items = itemService.getAllItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping(path = "/restaurant/menu/{restaurantMenuId}")
    @Operation(summary = "")
    public ResponseEntity<?> getItemsFromRestaurantMenu(
            @Parameter(name = "restaurantMenuId", description = "Restaurant menu id", example = "")
            @PathVariable(value = "restaurantMenuId") Long restaurantMenuId) {

        List<Item> items = itemService.getItemsFromMenu(restaurantMenuId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping(path = "/")
    @Operation(summary = "")
    public ResponseEntity<?> addItemToRestaurantMenu(
            @Parameter(name = "itemName", description = "Item name", example = "")
            @RequestParam String itemName,
            @Parameter(name = "itemDesc", description = "Item description", example = "")
            @RequestParam String itemDesc,
            @Parameter(name = "itemPrice", description = "Item price", example = "")
            @RequestParam Double itemPrice,
            @Parameter(name = "itemImage", description = "Item image", example = "")
            @RequestParam String itemImage,
            @Parameter(name = "itemIngredients", description = "Item ingredients", example = "")
            @RequestParam String itemIngredients,
            @Parameter(name = "itemAllergens", description = "Item allergens", example = "")
            @RequestParam String itemAllergens) {

        Item item = itemService.addItem(itemName, itemDesc, itemPrice, itemImage,
                itemIngredients, itemAllergens
        );

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{itemId}")
    @Operation(summary = "")
    public ResponseEntity<?> patchItemInRestaurantMenu(
            @Parameter(name = "itemId", description = "Item id", example = "")
            @PathVariable Long itemId,
            @Parameter(name = "itemName", description = "Item name", example = "")
            @RequestParam(required = false) String itemName,
            @Parameter(name = "itemDesc", description = "Item description", example = "")
            @RequestParam(required = false) String itemDesc,
            @Parameter(name = "itemPrice", description = "Item price", example = "")
            @RequestParam(required = false) Double itemPrice,
            @Parameter(name = "itemImage", description = "Item image", example = "")
            @RequestParam(required = false) String itemImage,
            @Parameter(name = "itemIngredients", description = "Item ingredients", example = "")
            @RequestParam(required = false) String itemIngredients,
            @Parameter(name = "itemAllergens", description = "Item allergens", example = "")
            @RequestParam(required = false) String itemAllergens) {

        Item item = itemService.patchItem(itemId, itemName, itemDesc, itemPrice, itemImage,
                itemIngredients, itemAllergens);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{itemId}")
    @Operation(summary = "")
    public ResponseEntity<?> deleteItemFromRestaurantMenu(
            @Parameter(name = "itemId", description = "Item id", example = "")
            @PathVariable Long itemId) {

        String response = itemService.deleteItem(itemId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.service.RestaurantService;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/all")
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getRestaurantsByOwnerId(@PathVariable Long ownerId) {
        List<Restaurant> restaurantList = restaurantService.getRestaurantsByOwnerId(ownerId);
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping("/theme/{theme}")
    public ResponseEntity<?> getRestaurantsByTheme(@PathVariable String theme) {
        List<Restaurant> restaurantList = restaurantService.getRestaurantsByTheme(theme);
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> createRestaurantByOwner(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) String theme,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String image) {
        // Assuming the owner is authenticated and their id is available.
        Restaurant restaurant = restaurantService.createRestaurant(name, desc, theme, address, phone, image);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PostMapping("/admin/{ownerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRestaurantByAdmin(
            @PathVariable Long ownerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) String theme,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String image) {
        Restaurant restaurant = restaurantService.createRestaurant(ownerId, name, desc, theme, address, phone, image);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PatchMapping("/{restaurantId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) String theme,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String image) {
        Restaurant restaurant = restaurantService.updateRestaurant(
                restaurantId, name, desc, theme,
                address, phone, image);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{restaurantId}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long restaurantId) {
        String response = restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


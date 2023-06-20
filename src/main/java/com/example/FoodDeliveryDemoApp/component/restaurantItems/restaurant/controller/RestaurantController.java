package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.controller;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import org.springframework.http.HttpStatus;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<?> createRestaurantByOwner(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) String theme,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String image,
            @RequestParam(required = false) AddressDTO address) {
        // Assuming the owner is authenticated and their id is available.
        Restaurant restaurant = restaurantService.createRestaurant(username, name, desc, theme, phone, image, address);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PostMapping("/admin/{ownerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createRestaurantByAdmin(
            @PathVariable Long ownerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) String theme,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String image,
            @RequestParam(required = false) AddressDTO address) {
        Restaurant restaurant = restaurantService.createRestaurant(ownerId, name, desc, theme, phone, image, address);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PatchMapping("/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    public ResponseEntity<?> updateRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) String theme,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String image,
            @RequestParam(required = false) AddressDTO address) {
        Restaurant restaurant = restaurantService.updateRestaurant(
                restaurantId, name, desc, theme,
                phone, image, address);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long restaurantId) {
        String response = restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


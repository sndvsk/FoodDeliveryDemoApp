package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.controller;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.RestaurantTheme;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto.RestaurantDTO;
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
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<RestaurantDTO> restaurantList = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantsByOwnerId(@PathVariable Long ownerId) {
        List<RestaurantDTO> restaurantList = restaurantService.getRestaurantsByOwnerId(ownerId);
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping("/theme/{theme}")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantsByTheme(@PathVariable String theme) {
        List<RestaurantDTO> restaurantList = restaurantService.getRestaurantsByTheme(theme);
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @PostMapping("/create/{ownerId}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<RestaurantDTO> createRestaurantByOwner(
            @PathVariable Long ownerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) RestaurantTheme theme,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String image,
            @RequestParam(required = false) AddressDTO address) {
        RestaurantDTO restaurant = restaurantService.createRestaurant(ownerId, name, desc, theme, phone, image, address);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PostMapping("/create-admin/{ownerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestaurantDTO> createRestaurantByAdmin(
            @PathVariable Long ownerId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) RestaurantTheme theme,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String image,
            @RequestParam(required = false) AddressDTO address) {
        RestaurantDTO restaurant = restaurantService.createRestaurant(ownerId, name, desc, theme, phone, image, address);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PatchMapping("/{ownerId}/update/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    public ResponseEntity<RestaurantDTO> updateRestaurant(
            @PathVariable Long ownerId,
            @PathVariable Long restaurantId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) RestaurantTheme theme,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String image,
            @RequestParam(required = false) AddressDTO address) {
        RestaurantDTO restaurant = restaurantService.updateRestaurant(
                ownerId, restaurantId, name, desc, theme, phone, image, address);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long restaurantId) {
        String response = restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


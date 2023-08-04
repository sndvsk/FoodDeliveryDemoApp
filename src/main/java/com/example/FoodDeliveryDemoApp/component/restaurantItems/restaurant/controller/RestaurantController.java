package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.controller;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto.RestaurantDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearerAuth")
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

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable Long restaurantId) {
        RestaurantDTO restaurant = restaurantService.getRestaurant(restaurantId);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PostMapping("/create/{ownerId}")
    @PreAuthorize("hasAnyAuthority('OWNER', 'ADMIN')")
    public ResponseEntity<RestaurantDTO> createRestaurant(
            @PathVariable Long ownerId,
            @RequestBody RestaurantDTO restaurantDto) {
        RestaurantDTO restaurant = restaurantService.createRestaurant(ownerId, restaurantDto);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    public ResponseEntity<RestaurantDTO> updateRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam Long ownerId,
            @RequestBody RestaurantDTO restaurantDto) {
        RestaurantDTO restaurant = restaurantService.updateRestaurant(
                restaurantId,
                ownerId,
                restaurantDto);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    public ResponseEntity<String> deleteRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam Long ownerId) {
        String response = restaurantService.deleteRestaurant(restaurantId, ownerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


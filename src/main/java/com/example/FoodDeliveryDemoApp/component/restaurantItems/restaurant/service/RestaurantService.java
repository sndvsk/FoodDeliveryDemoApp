package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.service;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.RestaurantTheme;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto.RestaurantDTO;

import java.util.List;
//import java.util.concurrent.CompletableFuture;

public interface RestaurantService {

    List<RestaurantDTO> getAllRestaurants();

    List<RestaurantDTO> getRestaurantsByOwnerId(Long ownerId);

    List<RestaurantDTO> getRestaurantsByTheme(String theme);

    RestaurantDTO getRestaurant(Long restaurantId);

    RestaurantDTO createRestaurant(Long ownerId, RestaurantDTO restaurantDTO);

/*    CompletableFuture<RestaurantDTO> createRestaurant(Long ownerId, String name, String desc, RestaurantTheme theme, String phone,
                                       String image, AddressDTO address);*/

    RestaurantDTO updateRestaurant(Long restaurantId, Long ownerId, RestaurantDTO restaurantDTO);

    String deleteRestaurant(Long restaurantId, Long ownerId);
}

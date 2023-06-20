package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAllRestaurants();

    List<Restaurant> getRestaurantsByOwnerId(Long ownerId);

    List<Restaurant> getRestaurantsByTheme(String theme);

    Restaurant createRestaurant(String username,
                                String name, String desc, String theme, String phone, String image, AddressDTO address);

    Restaurant createRestaurant(Long ownerId,
                                String name, String desc, String theme, String phone, String image, AddressDTO address);

    Restaurant updateRestaurant(Long restaurantId,
                                String name, String desc, String theme, String phone, String image, AddressDTO address);

    String deleteRestaurant(Long restaurantId);
}

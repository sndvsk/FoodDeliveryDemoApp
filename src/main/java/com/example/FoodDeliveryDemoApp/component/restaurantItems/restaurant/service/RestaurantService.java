package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAllRestaurants();

    List<Restaurant> getRestaurantsByOwnerId(Long ownerId);

    List<Restaurant> getRestaurantsByTheme(String theme);

    //Restaurant createRestaurant(Long ownerId, Restaurant restaurantData);

    Restaurant createRestaurant(String name, String desc, String theme, String address, String phone, String image);

    Restaurant createRestaurant(Long ownerId, String name, String desc, String theme, String address,
                                String phone, String image);

    Restaurant updateRestaurant(Long restaurantId, String name, String desc, String theme, String address,
                                String phone, String image);

    String deleteRestaurant(Long restaurantId);
}

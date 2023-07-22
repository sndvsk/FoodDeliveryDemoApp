package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain;

import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;

public enum RestaurantTheme {
    ASIAN,
    MEXICAN,
    ITALIAN,
    FRENCH,
    AMERICAN,
    INDIAN,
    MEDITERRANEAN,
    VEGAN,
    SEAFOOD,
    BBQ;

    public static RestaurantTheme fromString(String themeStr) {
        for (RestaurantTheme theme : RestaurantTheme.values()) {
            if (theme.name().equalsIgnoreCase(themeStr)) {
                return theme;
            }
        }
        throw new CustomNotFoundException("Invalid theme: " + themeStr.toUpperCase());
    }
}
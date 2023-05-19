package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByTheme(String theme);
}

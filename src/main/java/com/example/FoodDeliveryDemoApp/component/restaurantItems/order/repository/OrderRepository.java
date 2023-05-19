package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.repository;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

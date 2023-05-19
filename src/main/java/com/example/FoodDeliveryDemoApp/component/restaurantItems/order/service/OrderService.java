package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order getOrderById(Long id);

    List<Order> getOrdersByCustomerId(Long customerId);

    List<Order> getOrdersByRestaurantId(Long restaurantId);

    Order createOrder(Long customerId, Long restaurantId);

    Order updateOrder(Long id, String items);

    String deleteOrder(Long id);

}

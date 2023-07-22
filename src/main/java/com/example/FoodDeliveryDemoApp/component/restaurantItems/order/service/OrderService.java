package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(Long id);

    List<OrderDTO> getOrdersByCustomerId(Long customerId);

    List<OrderDTO> getOrdersByRestaurantId(Long restaurantId, Long ownerId);

    List<OrderDTO> getOrdersByRestaurantIdAndCustomerId(Long restaurantId, Long ownerId, Long customerId);

    OrderDTO createOrder(Long customerId, Long restaurantId);

    OrderDTO updateOrder(Long id, String city, String vehicleType, String items, Long customerId);

    String deleteOrder(Long id);
}

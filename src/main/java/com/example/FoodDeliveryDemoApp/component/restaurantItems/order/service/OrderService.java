package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(Long id);

    List<OrderDTO> getOrdersByCustomerId(Long customerId);

    List<OrderDTO> getOrdersByRestaurantId(Long restaurantId);

    OrderDTO createOrder(Long customerId, Long restaurantId);

    OrderDTO updateOrder(Long id, String city, String vehicleType, String items);

    String deleteOrder(Long id);

}

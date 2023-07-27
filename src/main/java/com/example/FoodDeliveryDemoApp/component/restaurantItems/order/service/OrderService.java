package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTOResponse;

import java.util.List;

public interface OrderService {

    List<OrderDTOResponse> getAllOrders();

    OrderDTOResponse getOrderById(Long id);

    List<OrderDTOResponse> getOrdersByCustomerIdByAdmin(Long customerId);

    List<OrderDTOResponse> getOrdersByCustomerId(String authorization, Long customerId);

    List<OrderDTOResponse> getOrdersByRestaurantId(Long restaurantId, Long ownerId);

    List<OrderDTOResponse> getOrdersByRestaurantIdAndCustomerId(Long restaurantId, Long ownerId, Long customerId);

    OrderDTO createOrder(Long customerId, Long restaurantId);

    OrderDTO updateOrder(Long id, String city, String vehicleType, String items, Long customerId);

    String deleteOrder(Long id);
}

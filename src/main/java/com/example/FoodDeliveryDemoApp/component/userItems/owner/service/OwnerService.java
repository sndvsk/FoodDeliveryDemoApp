package com.example.FoodDeliveryDemoApp.component.userItems.owner.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTO;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.dto.OwnerDTO;

public interface OwnerService {

    OwnerDTO getOwner(Long userId);

    OrderDTO acceptOrder(Long orderId, Long ownerId);

    OrderDTO rejectOrder(Long orderId, Long ownerId);

    //Long getIdByUsername(String username);

}

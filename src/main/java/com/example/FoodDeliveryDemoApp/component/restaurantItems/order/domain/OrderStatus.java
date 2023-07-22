package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain;

public enum OrderStatus {
    CREATED,        // The order has been created but no items have been added yet.
    SUBMITTED,      // The customer has submitted the order.
    ACCEPTED,       // The restaurant has accepted the order and started preparing it.
    READY,          // The restaurant has prepared the order and it's ready for pickup or delivery.
    ON_ROUTE,       // The order is in transit to the customer.
    DELIVERED,      // The order has been delivered to the customer.
    CANCELLED       // The order was cancelled before it could be delivered.
}

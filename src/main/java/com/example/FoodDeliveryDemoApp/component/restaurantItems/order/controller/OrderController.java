package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.controller;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.Order;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getOrderById(
            @PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(path = "/customer/{customerId}")
    public ResponseEntity<?> getOrdersByCustomer(
            @PathVariable Long customerId) {
        List<Order> order = orderService.getOrdersByCustomerId(customerId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(path = "/{restaurantId}")
    public ResponseEntity<?> getOrdersByRestaurant(
            @PathVariable Long restaurantId) {
        List<Order> order = orderService.getOrdersByRestaurantId(restaurantId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // fixme
    @PostMapping(path = "/restaurant/{restaurantId}/customer/{customerId}")
    public ResponseEntity<?> createOrder(
            @PathVariable Long customerId,
            @PathVariable Long restaurantId) {
        Order createdOrder = orderService.createOrder(customerId, restaurantId);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // fixme
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @PathVariable Long id, @RequestParam String items) {
        Order updated = orderService.updateOrder(id, items);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(
            @PathVariable Long id) {
        String response = orderService.deleteOrder(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

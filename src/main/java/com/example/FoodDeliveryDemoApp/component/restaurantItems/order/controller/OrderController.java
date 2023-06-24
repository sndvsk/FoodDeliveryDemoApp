package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.controller;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<OrderDTO> getOrderById(
            @PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(path = "/customer/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(
            @PathVariable Long customerId) {
        List<OrderDTO> order = orderService.getOrdersByCustomerId(customerId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(path = "/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    public ResponseEntity<List<OrderDTO>> getOrdersByRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam Long ownerId) {
        List<OrderDTO> order = orderService.getOrdersByRestaurantId(restaurantId, ownerId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping(path = "/{restaurantId}/customer/{customerId}")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    public ResponseEntity<List<OrderDTO>> getOrdersByRestaurantAndCustomer(
            @PathVariable Long restaurantId,
            @RequestParam Long ownerId,
            @PathVariable Long customerId) {
        List<OrderDTO> order = orderService.getOrdersByRestaurantIdAndCustomerId(restaurantId, ownerId, customerId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping(path = "/restaurant/{restaurantId}/customer/{customerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CUSTOMER')")
    public ResponseEntity<OrderDTO> createOrder(
            @PathVariable Long customerId,
            @PathVariable Long restaurantId) {
        OrderDTO createdOrder = orderService.createOrder(customerId, restaurantId);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Long id,
            @RequestParam String city,
            @RequestParam String vehicleType,
            @RequestParam String items,
            @RequestParam Long customerId) {
        OrderDTO updated = orderService.updateOrder(id, city, vehicleType, items, customerId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Long id) {
        String response = orderService.deleteOrder(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

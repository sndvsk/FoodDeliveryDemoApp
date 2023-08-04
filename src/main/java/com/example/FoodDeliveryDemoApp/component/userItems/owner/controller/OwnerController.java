package com.example.FoodDeliveryDemoApp.component.userItems.owner.controller;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTO;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.dto.OwnerDTO;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.service.OwnerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v2/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable Long ownerId) {
        OwnerDTO owner = ownerService.getOwner(ownerId);
        return ResponseEntity.ok(owner);
    }

    @PatchMapping("/accept-order/{orderId}")
    public ResponseEntity<OrderDTO> acceptOrder(
            @PathVariable Long orderId,
            @RequestParam Long ownerId) {
        OrderDTO order = ownerService.acceptOrder(orderId, ownerId);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/reject-order/{orderId}")
    public ResponseEntity<OrderDTO> rejectOrder(
            @PathVariable Long orderId,
            @RequestParam Long ownerId) {
        OrderDTO order = ownerService.rejectOrder(orderId, ownerId);
        return ResponseEntity.ok(order);
    }

}

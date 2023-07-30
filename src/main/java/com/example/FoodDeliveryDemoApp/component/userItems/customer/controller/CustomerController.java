package com.example.FoodDeliveryDemoApp.component.userItems.customer.controller;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v2/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{userId}/address")
    public ResponseEntity<AddressDTO> getAddress(
            @PathVariable Long userId) {

        AddressDTO response = customerService.getAddress(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{userId}/address")
    public ResponseEntity<AddressDTO> addAddress(
            @PathVariable Long userId,
            @RequestBody AddressDTO address) {

        AddressDTO response = customerService.addAddress(userId, address);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{userId}/address")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable Long userId,
            @RequestBody AddressDTO address) {

        AddressDTO response = customerService.updateAddress(userId, address);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

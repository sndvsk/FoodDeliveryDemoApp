package com.example.FoodDeliveryDemoApp.component.userItems.customer.controller;

import com.example.FoodDeliveryDemoApp.component.address.domain.Address;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTOMapper;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{username}/address")
    public ResponseEntity<AddressDTO> getAddress(
            @PathVariable String username) {

        Address savedAddress = customerService.getAddress(username);
        AddressDTO response = AddressDTOMapper.toDto(savedAddress);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{username}/address")
    public ResponseEntity<AddressDTO> addAddress(
            @PathVariable String username,
            @RequestBody Address address) {

        Address savedAddress = customerService.addAddress(username, address);
        AddressDTO response = AddressDTOMapper.toDto(savedAddress);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

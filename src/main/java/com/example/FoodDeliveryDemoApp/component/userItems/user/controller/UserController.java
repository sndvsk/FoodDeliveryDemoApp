package com.example.FoodDeliveryDemoApp.component.userItems.user.controller;

import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.UserDetailsDTO;
import com.example.FoodDeliveryDemoApp.component.userItems.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDetailsDTO> getUser(
            @PathVariable String username) {

        UserDetailsDTO customer = userService.getUserInformation(username);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PatchMapping("/update/{username}")
    public ResponseEntity<UserDetailsDTO> updateUser(
            @PathVariable String username,
            @RequestBody UserDetailsDTO customer) {

        UserDetailsDTO savedCustomer = userService.updateUserInformation(username, customer);

        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }

}

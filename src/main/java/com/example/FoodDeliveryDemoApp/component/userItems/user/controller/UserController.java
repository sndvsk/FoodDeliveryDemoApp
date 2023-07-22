package com.example.FoodDeliveryDemoApp.component.userItems.user.controller;

import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.UserDTO;
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
    public ResponseEntity<UserDTO> getUser(
            @PathVariable String username) {

        UserDTO customer = userService.getUserInformation(username);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PatchMapping("/update/{username}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String username,
            @RequestBody UserDTO customer) {

        UserDTO savedCustomer = userService.updateUserInformation(username, customer);

        return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
    }

}

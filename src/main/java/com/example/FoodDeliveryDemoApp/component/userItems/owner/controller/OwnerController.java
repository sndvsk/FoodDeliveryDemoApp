package com.example.FoodDeliveryDemoApp.component.userItems.owner.controller;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.service.OwnerService;
import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

/*    @PostMapping("/register")
    public ResponseEntity<Owner> registerOwner(@RequestBody RegisterRequest request) {

        Owner savedOwner = ownerService.registerOwner(request);

        return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
    }*/

}

package com.example.FoodDeliveryDemoApp.component.userItems.owner.controller;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.service.OwnerService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
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

package com.example.FoodDeliveryDemoApp.component.userItems.owner.service;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.RegisterRequest;

import java.util.Optional;

public interface OwnerService {

    Long getCurrentAccount();

    Owner registerOwner(RegisterRequest request);

    Optional<Owner> getOwnerByUsername(String username);

}

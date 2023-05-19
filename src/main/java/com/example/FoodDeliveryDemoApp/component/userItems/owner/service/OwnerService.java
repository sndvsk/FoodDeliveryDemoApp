package com.example.FoodDeliveryDemoApp.component.userItems.owner.service;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;

public interface OwnerService {

    Long getCurrentAccount();

    Owner registerOwner(Owner owner);

}

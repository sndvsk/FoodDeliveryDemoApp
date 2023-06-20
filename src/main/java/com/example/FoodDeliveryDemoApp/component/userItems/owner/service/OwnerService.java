package com.example.FoodDeliveryDemoApp.component.userItems.owner.service;

public interface OwnerService {

    Long getCurrentAccount();

    //Owner updateUserInformation(String username, UserDetailsDTO owner);

    //Owner registerOwner(RegisterRequest request);

    //Owner getOwnerByUsername(String username);

    Long getIdByUsername(String username);
}

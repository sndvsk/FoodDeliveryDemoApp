package com.example.FoodDeliveryDemoApp.component.userItems.customer.service;

import com.example.FoodDeliveryDemoApp.component.address.domain.Address;

public interface CustomerService {

    //Customer getCustomerByUsername(String username);

    //Customer updateUserInformation(String username, UserDetailsDTO updatedCustomer);

    Address addAddress(String username, Address address);

    Address getAddress(String username);

    //UserDetailsDTO getUserInformation(String username);
}

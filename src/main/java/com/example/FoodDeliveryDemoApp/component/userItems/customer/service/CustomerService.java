package com.example.FoodDeliveryDemoApp.component.userItems.customer.service;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;

public interface CustomerService {

    AddressDTO addAddress(Long userId, AddressDTO address);

    AddressDTO getAddress(Long userId);

    AddressDTO updateAddress(Long userId, AddressDTO address);

}

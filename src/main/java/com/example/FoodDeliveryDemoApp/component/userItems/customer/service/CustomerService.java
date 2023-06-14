package com.example.FoodDeliveryDemoApp.component.userItems.customer.service;

import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;

import java.util.Optional;

public interface CustomerService {

    Customer registerCustomer(Customer customer);

    Optional<Customer> getCustomerByUsername(String username);

}

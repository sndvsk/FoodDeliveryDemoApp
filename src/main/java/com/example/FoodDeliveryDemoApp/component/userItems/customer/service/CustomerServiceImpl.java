package com.example.FoodDeliveryDemoApp.component.userItems.customer.service;

import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer registerCustomer(Customer customer) {
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username already exists");
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer savedCustomer = customerRepository.save(customer);

        return savedCustomer;
    }

}

package com.example.FoodDeliveryDemoApp.component.userItems.customer.repository;

import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByEmail(String email);
}

package com.example.FoodDeliveryDemoApp.component.address.repository;

import com.example.FoodDeliveryDemoApp.component.address.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "SELECT * FROM addresses WHERE customer_id = :customerId", nativeQuery = true)
    Optional<Address> findAddressByCustomerId(@Param("customerId") Long customerId);

}

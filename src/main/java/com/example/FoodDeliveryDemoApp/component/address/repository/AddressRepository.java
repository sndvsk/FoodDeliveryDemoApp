package com.example.FoodDeliveryDemoApp.component.address.repository;

import com.example.FoodDeliveryDemoApp.component.address.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    //List<Address> findAddressesByCustomer_Id(Long id);

    Optional<Address> findAddressByCustomer_Id(Long id);

}

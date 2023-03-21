package com.example.FoodDeliveryDemoApp.repository;

import com.example.FoodDeliveryDemoApp.model.DeliveryFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface DeliveryFeeRepository extends JpaRepository<DeliveryFee, Long> {

    List<DeliveryFee> findByWeatherId(Long weatherId);

    List<DeliveryFee> findAllByCity(String city);

    Optional<DeliveryFee> findById(Long id);

    List<DeliveryFee> findAll();
}


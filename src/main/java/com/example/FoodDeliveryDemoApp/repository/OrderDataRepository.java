package com.example.FoodDeliveryDemoApp.repository;

import com.example.FoodDeliveryDemoApp.model.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface OrderDataRepository extends JpaRepository<OrderData, Long> {

    List<OrderData> findByWeatherId(Long weatherId);

    List<OrderData> findAllByCity(String city);
}


package com.example.FoodDeliveryDemoApp.repository;

import com.example.FoodDeliveryDemoApp.model.DeliveryFee;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryFeeRepository extends JpaRepository<DeliveryFee, Long> {

    @SuppressWarnings("unused")
    List<DeliveryFee> findByWeatherId(Long weatherId);

    @SuppressWarnings("unused")
    List<DeliveryFee> findAllByCity(String city);

    @NotNull
    Optional<DeliveryFee> findById(@NotNull Long id);

    @NotNull
    List<DeliveryFee> findAll();

    void deleteById(@NotNull Long id);

    boolean existsById(@NotNull Long id);

}


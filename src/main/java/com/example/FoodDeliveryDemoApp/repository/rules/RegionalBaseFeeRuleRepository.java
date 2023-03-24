package com.example.FoodDeliveryDemoApp.repository.rules;

import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionalBaseFeeRuleRepository extends JpaRepository<RegionalBaseFeeRule, Long> {

    List<RegionalBaseFeeRule> findAll();

    Optional<RegionalBaseFeeRule> findByCityAndVehicleType(String city, String vehicleType);
}

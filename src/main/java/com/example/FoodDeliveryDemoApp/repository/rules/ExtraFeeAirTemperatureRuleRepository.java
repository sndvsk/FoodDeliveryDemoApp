package com.example.FoodDeliveryDemoApp.repository.rules;

import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeAirTemperatureRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraFeeAirTemperatureRuleRepository extends JpaRepository<ExtraFeeAirTemperatureRule, Long> {

}

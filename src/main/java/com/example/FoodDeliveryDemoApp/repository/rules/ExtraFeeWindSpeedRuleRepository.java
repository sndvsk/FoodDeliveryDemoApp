package com.example.FoodDeliveryDemoApp.repository.rules;

import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWindSpeedRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraFeeWindSpeedRuleRepository extends JpaRepository<ExtraFeeWindSpeedRule, Long> {

}

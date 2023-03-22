package com.example.FoodDeliveryDemoApp.repository.rules;

import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseFeeRuleRepository extends JpaRepository<RegionalBaseFeeRule, Long> {

}

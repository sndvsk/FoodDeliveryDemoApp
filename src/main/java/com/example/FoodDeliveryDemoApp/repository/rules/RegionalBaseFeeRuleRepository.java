package com.example.FoodDeliveryDemoApp.repository.rules;

import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionalBaseFeeRuleRepository extends JpaRepository<RegionalBaseFeeRule, Long> {

    List<RegionalBaseFeeRule> findAll();

}

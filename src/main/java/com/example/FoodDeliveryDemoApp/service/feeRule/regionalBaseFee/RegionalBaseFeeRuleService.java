package com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee;

import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;

import java.util.List;

public interface RegionalBaseFeeRuleService {

    List<RegionalBaseFeeRule> getAllRegionalBaseFeeRules();

    RegionalBaseFeeRule addBaseFeeRule(String city, String vehicleType, Double fee);

    RegionalBaseFeeRule getRegionalBaseFeeRuleById(Long id);

    RegionalBaseFeeRule patchRegionalBaseFeeRuleById(Long id, String city, String vehicleType, Double fee);

    String deleteRegionalBaseFeeRuleById(Long id);

}

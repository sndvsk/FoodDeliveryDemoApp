package com.example.FoodDeliveryDemoApp.service.FeeRule.RegionalBaseFee;

import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;

import java.util.List;

public interface RegionalBaseFeeRuleService {

    List<RegionalBaseFeeRule> getAllRegionalBaseFeeRules();

    RegionalBaseFeeRule addBaseFeeRule();

    RegionalBaseFeeRule getRegionalBaseFeeRuleById(Long id);

    RegionalBaseFeeRule patchRegionalBaseFeeRuleById(Long id);

    String deleteRegionalBaseFeeRuleById(Long id);

}

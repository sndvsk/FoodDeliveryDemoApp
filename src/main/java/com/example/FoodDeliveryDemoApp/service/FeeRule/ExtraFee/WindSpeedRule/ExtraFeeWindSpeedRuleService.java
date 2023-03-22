package com.example.FoodDeliveryDemoApp.service.FeeRule.ExtraFee.WindSpeedRule;

import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWindSpeedRule;

import java.util.List;

public interface ExtraFeeWindSpeedRuleService {

    List<ExtraFeeWindSpeedRule> getAllExtraFeeWindSpeedRules();

    ExtraFeeWindSpeedRule addExtraFeeWindSpeedRule();

    ExtraFeeWindSpeedRule getExtraFeeWindSpeedRuleById(Long id);

    ExtraFeeWindSpeedRule patchExtraFeeWindSpeedRuleById(Long id);

    String deleteExtraFeeWindSpeedRuleById(Long id);

}

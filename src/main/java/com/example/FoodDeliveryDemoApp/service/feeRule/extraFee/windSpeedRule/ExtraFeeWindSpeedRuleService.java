package com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.windSpeedRule;

import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWindSpeedRule;

import java.util.List;

public interface ExtraFeeWindSpeedRuleService {

    List<ExtraFeeWindSpeedRule> getAllExtraFeeWindSpeedRules();

    ExtraFeeWindSpeedRule addExtraFeeWindSpeedRule(Double startWindSpeedRange, Double endWindSpeedRange, Double fee);

    ExtraFeeWindSpeedRule getExtraFeeWindSpeedRuleById(Long id);

    ExtraFeeWindSpeedRule patchExtraFeeWindSpeedRuleById(Long id, Double startWindSpeedRange, Double endWindSpeedRange, Double fee);

    String deleteExtraFeeWindSpeedRuleById(Long id);

}

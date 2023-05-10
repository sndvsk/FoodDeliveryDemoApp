package com.example.FoodDeliveryDemoApp.component.calculations.feeRule.service.extraFee.windSpeedRule;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeWindSpeedRule;

import java.util.List;

public interface ExtraFeeWindSpeedRuleService {

    List<ExtraFeeWindSpeedRule> getAllExtraFeeWindSpeedRules();

    ExtraFeeWindSpeedRule addExtraFeeWindSpeedRule(
            Double startWindSpeedRange, Double endWindSpeedRange, Double fee);

    ExtraFeeWindSpeedRule getExtraFeeWindSpeedRuleById(Long id);

    ExtraFeeWindSpeedRule patchExtraFeeWindSpeedRuleById(
            Long id, Double startWindSpeedRange, Double endWindSpeedRange, Double fee);

    String deleteExtraFeeWindSpeedRuleById(Long id);

    ExtraFeeWindSpeedRule getByWindSpeed(Double windSpeed);
}

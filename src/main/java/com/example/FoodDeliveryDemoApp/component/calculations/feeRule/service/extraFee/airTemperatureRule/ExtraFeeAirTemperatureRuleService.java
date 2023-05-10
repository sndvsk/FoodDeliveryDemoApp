package com.example.FoodDeliveryDemoApp.component.calculations.feeRule.service.extraFee.airTemperatureRule;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeAirTemperatureRule;

import java.util.List;

public interface ExtraFeeAirTemperatureRuleService {

    List<ExtraFeeAirTemperatureRule> getAllExtraFeeAirTemperatureRules();

    ExtraFeeAirTemperatureRule addExtraFeeAirTemperatureRule(
            Double startTemperatureRange, Double endTemperatureRange, Double fee);

    ExtraFeeAirTemperatureRule getExtraFeeAirTemperatureRuleById(Long id);

    ExtraFeeAirTemperatureRule patchExtraFeeAirTemperatureRuleById(
            Long id, Double startTemperatureRange, Double endTemperatureRange, Double fee);

    String deleteExtraFeeAirTemperatureRuleById(Long id);

    ExtraFeeAirTemperatureRule getByTemperature(double airTemperature);
}

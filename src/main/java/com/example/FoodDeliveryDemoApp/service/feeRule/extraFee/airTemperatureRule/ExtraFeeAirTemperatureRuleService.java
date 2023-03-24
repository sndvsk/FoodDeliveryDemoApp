package com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.airTemperatureRule;

import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeAirTemperatureRule;

import java.util.List;

public interface ExtraFeeAirTemperatureRuleService {

    List<ExtraFeeAirTemperatureRule> getAllExtraFeeAirTemperatureRules();

    ExtraFeeAirTemperatureRule addExtraFeeAirTemperatureRule(Double startTemperatureRange, Double endTemperatureRange, Double fee);

    ExtraFeeAirTemperatureRule getExtraFeeAirTemperatureRuleById(Long id);

    ExtraFeeAirTemperatureRule patchExtraFeeAirTemperatureRuleById(Long id, Double startTemperatureRange, Double endTemperatureRange, Double fee);

    String deleteExtraFeeAirTemperatureRuleById(Long id);

    ExtraFeeAirTemperatureRule getByTemperature(double airTemperature);
}

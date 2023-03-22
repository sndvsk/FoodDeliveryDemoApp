package com.example.FoodDeliveryDemoApp.service.FeeRule.ExtraFee.AirTemperatureRule;

import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeAirTemperatureRule;

import java.util.List;

public interface ExtraFeeAirTemperatureRuleService {

    List<ExtraFeeAirTemperatureRule> getAllExtraFeeAirTemperatureRules();

    ExtraFeeAirTemperatureRule addExtraFeeAirTemperatureRule();

    ExtraFeeAirTemperatureRule getExtraFeeAirTemperatureRuleById(Long id);

    ExtraFeeAirTemperatureRule patchExtraFeeAirTemperatureRuleById(Long id);

    String deleteExtraFeeAirTemperatureRuleById(Long id);

}

package com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.weatherPhenomenonRule;

import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWeatherPhenomenonRule;

import java.util.List;

public interface ExtraFeeWeatherPhenomenonRuleService {

    List<ExtraFeeWeatherPhenomenonRule> getAllExtraFeeWeatherPhenomenonRules();

    ExtraFeeWeatherPhenomenonRule addExtraFeeWeatherPhenomenonRule(String weatherPhenomenonName, Double fee);

    ExtraFeeWeatherPhenomenonRule getExtraFeeWeatherPhenomenonRuleById(Long id);

    ExtraFeeWeatherPhenomenonRule patchExtraFeeWeatherPhenomenonRuleById(Long id, String weatherPhenomenonName, Double fee);

    String deleteExtraFeeWeatherPhenomenonRuleById(Long id);

    ExtraFeeWeatherPhenomenonRule getByWeatherPhenomenonName(String weatherPhenomenon);
}

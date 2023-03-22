package com.example.FoodDeliveryDemoApp.service.FeeRule.ExtraFee.WeatherPhenomenonRule;

import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWeatherPhenomenonRule;

import java.util.List;

public interface ExtraFeeWeatherPhenomenonRuleService {

    List<ExtraFeeWeatherPhenomenonRule> getAllExtraFeeWeatherPhenomenonRules();

    ExtraFeeWeatherPhenomenonRule addExtraFeeWeatherPhenomenonRule();

    ExtraFeeWeatherPhenomenonRule getExtraFeeWeatherPhenomenonRuleById(Long id);

    ExtraFeeWeatherPhenomenonRule patchExtraFeeWeatherPhenomenonRuleById(Long id);

    String deleteExtraFeeWeatherPhenomenonRuleById(Long id);

    ExtraFeeWeatherPhenomenonRule findByWeatherPhenomenonName(String weatherPhenomenon);

}

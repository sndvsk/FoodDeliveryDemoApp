package com.example.FoodDeliveryDemoApp.service.FeeRule;


import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;

import java.util.List;

public interface FeeRuleService {

    List<RegionalBaseFeeRule> getAllRegionalBaseFeeRules();

    RegionalBaseFeeRule addBaseFeeRule();

    RegionalBaseFeeRule getRegionalBaseFeeRuleById(Long id);

    RegionalBaseFeeRule patchRegionalBaseFeeRuleById(Long id);

    String deleteRegionalBaseFeeRuleById(Long id);

    List<ExtraFeeAirTemperatureRule> getAllExtraFeeAirTemperatureRules();

    ExtraFeeAirTemperatureRule addExtraFeeAirTemperatureRule();

    ExtraFeeAirTemperatureRule getExtraFeeAirTemperatureRuleById(Long id);

    ExtraFeeAirTemperatureRule patchExtraFeeAirTemperatureRuleById(Long id);

    String deleteExtraFeeAirTemperatureRuleById(Long id);

    List<ExtraFeeWindSpeedRule> getAllExtraFeeWindSpeedRules();

    ExtraFeeWindSpeedRule addExtraFeeWindSpeedRule();

    ExtraFeeWindSpeedRule getExtraFeeWindSpeedRuleById(Long id);

    ExtraFeeWindSpeedRule patchExtraFeeWindSpeedRuleById(Long id);

    String deleteExtraFeeWindSpeedRuleById(Long id);

    List<ExtraFeeWeatherPhenomenonRule> getAllExtraFeeWeatherPhenomenonRules();

    ExtraFeeWeatherPhenomenonRule addExtraFeeWeatherPhenomenonRule();

    ExtraFeeWeatherPhenomenonRule getExtraFeeWeatherPhenomenonRuleById(Long id);

    ExtraFeeWeatherPhenomenonRule patchExtraFeeWeatherPhenomenonRuleById(Long id);

    String deleteExtraFeeWeatherPhenomenonRuleById(Long id);

    ExtraFeeWeatherPhenomenonRule findByWeatherPhenomenonName(String weatherPhenomenon);
}

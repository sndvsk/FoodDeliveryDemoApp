package com.example.FoodDeliveryDemoApp.service.FeeRule;

import org.springframework.stereotype.Component;


public abstract class FeeRuleServiceImpl implements FeeRuleService {

}

/*
    public FeeRule getRule(String feeType, Long id) {

        switch (feeType) {
            case "base-fee" -> {
                Optional<RegionalBaseFeeRule> baseFeeRule = baseFeeRuleRepository.findById(id);
                if (baseFeeRule.isPresent()) {
                    return baseFeeRule.get();
                } else {
                    throw new FeeRuleNotFoundException("This base fee rule does not exist");
                }
            }
            case "extra-fee-temp" -> {
                Optional<ExtraFeeAirTemperatureRule> extraFeeRule = airTemperatureRepository.findById(id);
                if (extraFeeRule.isPresent()) {
                    return extraFeeRule.get();
                } else {
                    throw new FeeRuleNotFoundException("This extra fee rule for air temperature does not exist");
                }
            }
            case "extra-fee-wind" -> {
                Optional<ExtraFeeWindSpeedRule> extraFeeRule = windSpeedRepository.findById(id);
                if (extraFeeRule.isPresent()) {
                    return extraFeeRule.get();
                } else {
                    throw new FeeRuleNotFoundException("This extra fee rule for wind speed does not exist");
                }
            }
            case "extra-fee-phenomenon" -> {
                Optional<ExtraFeeWeatherPhenomenonRule> extraFeeRule = weatherPhenomenonRepository.findById(id);
                if (extraFeeRule.isPresent()) {
                    return extraFeeRule.get();
                } else {
                    throw new FeeRuleNotFoundException("This extra fee rule for weather phenomenon does not exist");
                }
            }
            default -> throw new FeeRuleBadRequestException("No such rule type");
        }

    }

    public ExtraFeeWeatherPhenomenonRule findByWeatherPhenomenonName(String weatherPhenomenonName) {
        Optional<ExtraFeeWeatherPhenomenonRule> weatherPhenomenon = weatherPhenomenonRepository.findByWeatherPhenomenonName(weatherPhenomenonName);
        return weatherPhenomenon.
                orElseThrow(() -> new FeeRuleNotFoundException(String.format("Extra fee rule for this: ´%s´ weather phenomenon does not exist", weatherPhenomenonName)));
    }


    public List<RegionalBaseFeeRule> getAllRegionalBaseFeeRules() {
        return null;
    }

    public RegionalBaseFeeRule addBaseFeeRule() {
        return null;
    }

    public RegionalBaseFeeRule getRegionalBaseFeeRuleById(Long id) {
        return null;
    }

    public RegionalBaseFeeRule patchRegionalBaseFeeRuleById(Long id) {
        return null;
    }

    public String deleteRegionalBaseFeeRuleById(Long id) {
        return null;
    }


    public List<ExtraFeeAirTemperatureRule> getAllExtraFeeAirTemperatureRules() {
        return null;
    }

    public ExtraFeeAirTemperatureRule addExtraFeeAirTemperatureRule() {
        return null;
    }

    public ExtraFeeAirTemperatureRule getExtraFeeAirTemperatureRuleById(Long id) {
        return null;
    }

    public ExtraFeeAirTemperatureRule patchExtraFeeAirTemperatureRuleById(Long id) {
        return null;
    }

    public String deleteExtraFeeAirTemperatureRuleById(Long id) {
        return null;
    }


    public List<ExtraFeeWindSpeedRule> getAllExtraFeeWindSpeedRules() {
        return null;
    }

    public ExtraFeeWindSpeedRule addExtraFeeWindSpeedRule() {
        return null;
    }

    public ExtraFeeWindSpeedRule getExtraFeeWindSpeedRuleById(Long id) {
        return null;
    }

    public ExtraFeeWindSpeedRule patchExtraFeeWindSpeedRuleById(Long id) {
        return null;
    }

    public String deleteExtraFeeWindSpeedRuleById(Long id) {
        return null;
    }


    public List<ExtraFeeWeatherPhenomenonRule> getAllExtraFeeWeatherPhenomenonRules() {
        return null;
    }

    public ExtraFeeWeatherPhenomenonRule addExtraFeeWeatherPhenomenonRule() {
        return null;
    }

    public ExtraFeeWeatherPhenomenonRule getExtraFeeWeatherPhenomenonRuleById(Long id) {
        return null;
    }

    public ExtraFeeWeatherPhenomenonRule patchExtraFeeWeatherPhenomenonRuleById(Long id) {
        return null;
    }

    public String deleteExtraFeeWeatherPhenomenonRuleById(Long id) {
        return null;
    }

 */
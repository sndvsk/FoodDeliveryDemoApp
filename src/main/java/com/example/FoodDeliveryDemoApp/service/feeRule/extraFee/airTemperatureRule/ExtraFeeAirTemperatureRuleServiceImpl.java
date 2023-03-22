package com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.airTemperatureRule;

import com.example.FoodDeliveryDemoApp.exception.feeRule.FeeRuleBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.feeRule.FeeRuleNotFoundException;
import com.example.FoodDeliveryDemoApp.exception.weatherData.WeatherDataBadRequestException;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeAirTemperatureRuleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ExtraFeeAirTemperatureRuleServiceImpl implements ExtraFeeAirTemperatureRuleService {

    private final ExtraFeeAirTemperatureRuleRepository airTemperatureRuleRepository;

    protected ExtraFeeAirTemperatureRuleServiceImpl(ExtraFeeAirTemperatureRuleRepository airTemperatureRuleRepository) {
        this.airTemperatureRuleRepository = airTemperatureRuleRepository;
    }

    public List<ExtraFeeAirTemperatureRule> getAllExtraFeeAirTemperatureRules() {
        List<ExtraFeeAirTemperatureRule> ruleList = airTemperatureRuleRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new FeeRuleNotFoundException("No extra fee air temperature rules in the database.");
        } else {
            return ruleList;
        }
    }

    public ExtraFeeAirTemperatureRule addExtraFeeAirTemperatureRule(Double startTemperatureRange, Double endTemperatureRange, Double fee) {

        validateRequiredInputs(startTemperatureRange, endTemperatureRange, fee);
        validateInputs(startTemperatureRange, endTemperatureRange, fee);

        ExtraFeeAirTemperatureRule rule = new ExtraFeeAirTemperatureRule();

        rule.setStartAirTemperatureRange(startTemperatureRange);
        rule.setEndAirTemperatureRange(endTemperatureRange);
        rule.setFee(fee);

        return airTemperatureRuleRepository.save(rule);
    }

    private void validateRequiredInputs(Double startTemperatureRange, Double endTemperatureRange, Double fee) {
        if (startTemperatureRange == null) {
            throw new FeeRuleBadRequestException("Start of air temperature range must be provided");
        }
        if (endTemperatureRange == null) {
            throw new FeeRuleBadRequestException("End of air temperature range must be provided");
        }
        if (fee == null) {
            throw new FeeRuleBadRequestException("Fee must be provided");
        }
    }

    private void validateInputs(Double startTemperatureRange, Double endTemperatureRange, Double fee) {

        // todo validate considering existing ranges in repository

        if (startTemperatureRange != null && startTemperatureRange < -273.15) {
            throw new WeatherDataBadRequestException(String.format("Provided air temperature: ´%s´ is lower than absolute zero.", startTemperatureRange));
        }
        if (endTemperatureRange != null && endTemperatureRange < -273.15) {
            throw new FeeRuleBadRequestException(String.format("Provided air temperature: ´%s´ is lower than absolute zero.", endTemperatureRange));
        }
        //noinspection ConstantConditions
        if (endTemperatureRange < startTemperatureRange) {
            throw new FeeRuleBadRequestException(String.format("Provided air temperature range is invalid, end: ´%s´ is lower than start: ´%s´", endTemperatureRange, startTemperatureRange));
        }
    }

    public ExtraFeeAirTemperatureRule getExtraFeeAirTemperatureRuleById(Long id) {
        Optional<ExtraFeeAirTemperatureRule> rule = airTemperatureRuleRepository.findById(id);
        return rule
                .orElseThrow(() -> new FeeRuleNotFoundException(String.format("Extra fee air temperature rule for this id: ´%s´ does not exist", id)));
    }

    public ExtraFeeAirTemperatureRule patchExtraFeeAirTemperatureRuleById(Long id, Double startTemperatureRange, Double endTemperatureRange, Double fee) {

        validateInputs(startTemperatureRange, endTemperatureRange, fee);

        Optional<ExtraFeeAirTemperatureRule> rule = airTemperatureRuleRepository.findById(id);

        ExtraFeeAirTemperatureRule patchedRule = rule
                .orElseThrow(() -> new FeeRuleNotFoundException(String.format("Extra fee air temperature rule for this id: ´%s´ does not exist", id)));

        if (startTemperatureRange != null) {
            patchedRule.startAirTemperatureRange = startTemperatureRange;
        }
        if (endTemperatureRange != null) {
            patchedRule.endAirTemperatureRange = endTemperatureRange;
        }
        if (fee != null) {
            patchedRule.fee = fee;
        }

        return airTemperatureRuleRepository.save(patchedRule);
    }

    public String deleteExtraFeeAirTemperatureRuleById(Long id) {
        if (airTemperatureRuleRepository.existsById(id)) {
            airTemperatureRuleRepository.deleteById(id);
            return String.format("Extra fee air temperature rule with id: ´%s´ was deleted", id);
        } else {
            throw new FeeRuleNotFoundException(String.format("Extra fee air temperature rule for this id: ´%s´ does not exist", id));
        }
    }

}

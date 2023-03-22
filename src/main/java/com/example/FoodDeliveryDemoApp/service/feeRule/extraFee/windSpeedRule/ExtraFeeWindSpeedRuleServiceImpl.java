package com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.windSpeedRule;

import com.example.FoodDeliveryDemoApp.exception.feeRule.FeeRuleBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.feeRule.FeeRuleNotFoundException;
import com.example.FoodDeliveryDemoApp.exception.weatherData.WeatherDataBadRequestException;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeWindSpeedRuleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ExtraFeeWindSpeedRuleServiceImpl implements ExtraFeeWindSpeedRuleService {

    private final ExtraFeeWindSpeedRuleRepository windSpeedRuleRepository;

    public ExtraFeeWindSpeedRuleServiceImpl(ExtraFeeWindSpeedRuleRepository windSpeedRuleRepository) {
        this.windSpeedRuleRepository = windSpeedRuleRepository;
    }

    public List<ExtraFeeWindSpeedRule> getAllExtraFeeWindSpeedRules() {
        List<ExtraFeeWindSpeedRule> ruleList = windSpeedRuleRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new FeeRuleNotFoundException("No extra fee wind speed rules in the database.");
        } else {
            return ruleList;
        }
    }

    public ExtraFeeWindSpeedRule addExtraFeeWindSpeedRule(Double startWindSpeedRange, Double endWindSpeedRange, Double fee) {

        validateRequiredInputs(startWindSpeedRange, endWindSpeedRange, fee);
        validateInputs(startWindSpeedRange, endWindSpeedRange);

        ExtraFeeWindSpeedRule rule = new ExtraFeeWindSpeedRule();

        rule.setStartWindSpeedRange(startWindSpeedRange);
        rule.setEndWindSpeedRange(endWindSpeedRange);
        rule.setFee(fee);

        return windSpeedRuleRepository.save(rule);
    }

    private void validateRequiredInputs(Double startWindSpeedRange, Double endWindSpeedRange, Double fee) {
        if (startWindSpeedRange == null) {
            throw new FeeRuleBadRequestException("Start of wind speed range must be provided");
        }
        if (endWindSpeedRange == null) {
            throw new FeeRuleBadRequestException("End of wind speed range must be provided");
        }
        if (fee == null) {
            throw new FeeRuleBadRequestException("Fee must be provided");
        }
    }

    private void validateInputs(Double startWindSpeedRange, Double endWindSpeedRange) {

        // todo validate considering existing ranges in repository

        if (startWindSpeedRange != null && startWindSpeedRange < 0.0) {
            throw new WeatherDataBadRequestException(String.format("Provided wind speed: ´%s´ is lower than zero.", startWindSpeedRange));
        }
        if (endWindSpeedRange != null && endWindSpeedRange < 0.0) {
            throw new FeeRuleBadRequestException(String.format("Provided wind speed: ´%s´ is lower than zero.", endWindSpeedRange));
        }
        //noinspection ConstantConditions
        if (endWindSpeedRange < startWindSpeedRange) {
            throw new FeeRuleBadRequestException(String.format("Provided wind speed range is invalid, end: ´%s´ is lower than start: ´%s´", endWindSpeedRange, startWindSpeedRange));
        }
    }

    public ExtraFeeWindSpeedRule getExtraFeeWindSpeedRuleById(Long id) {
        Optional<ExtraFeeWindSpeedRule> rule = windSpeedRuleRepository.findById(id);
        return rule
                .orElseThrow(() -> new FeeRuleNotFoundException(String.format("Extra fee wind speed rule for this id: ´%s´ does not exist", id)));
    }

    public ExtraFeeWindSpeedRule patchExtraFeeWindSpeedRuleById(Long id, Double startWindSpeedRange, Double endWindSpeedRange, Double fee) {

        validateInputs(startWindSpeedRange, endWindSpeedRange);

        Optional<ExtraFeeWindSpeedRule> rule = windSpeedRuleRepository.findById(id);

        ExtraFeeWindSpeedRule patchedRule = rule
                .orElseThrow(() -> new FeeRuleNotFoundException(String.format("Extra fee wind speed rule for this id: ´%s´ does not exist", id)));

        if (startWindSpeedRange != null) {
            patchedRule.startWindSpeedRange = startWindSpeedRange;
        }
        if (endWindSpeedRange != null) {
            patchedRule.endWindSpeedRange = endWindSpeedRange;
        }
        if (fee != null) {
            patchedRule.fee = fee;
        }

        return windSpeedRuleRepository.save(patchedRule);
    }

    public String deleteExtraFeeWindSpeedRuleById(Long id) {
        if (windSpeedRuleRepository.existsById(id)) {
            windSpeedRuleRepository.deleteById(id);
            return String.format("Extra fee wind speed rule with id: ´%s´ was deleted", id);
        } else {
            throw new FeeRuleNotFoundException(String.format("Extra fee wind speed rule for this id: ´%s´ does not exist", id));
        }
    }

}

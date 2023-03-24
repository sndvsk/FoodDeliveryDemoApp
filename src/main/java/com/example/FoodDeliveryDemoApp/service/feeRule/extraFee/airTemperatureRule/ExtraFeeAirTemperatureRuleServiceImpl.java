package com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.airTemperatureRule;

import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
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

    private void validateRequiredInputs(Double startTemperatureRange, Double endTemperatureRange, Double fee) {
        if (startTemperatureRange == null) {
            throw new CustomBadRequestException("Start of air temperature range must be provided");
        }
        if (endTemperatureRange == null) {
            throw new CustomBadRequestException("End of air temperature range must be provided");
        }
        if (fee == null) {
            throw new CustomBadRequestException("Fee must be provided");
        }
    }

    private void validateInputs(Double startTemperatureRange, Double endTemperatureRange, Double fee) {

        if (fee != null && fee < 0.0) {
            throw new CustomBadRequestException(String.format("Fee: ´%s´ must be positive", fee));
        }

        if (startTemperatureRange != null && startTemperatureRange < -273.15) {
            throw new CustomBadRequestException(String.format("Provided air temperature: ´%s´ is lower than absolute zero.", startTemperatureRange));
        }
        if (endTemperatureRange != null && endTemperatureRange < -273.15) {
            throw new CustomBadRequestException(String.format("Provided air temperature: ´%s´ is lower than absolute zero.", endTemperatureRange));
        }
        //noinspection ConstantConditions
        if (endTemperatureRange < startTemperatureRange) {
            throw new CustomBadRequestException(String.format("Provided air temperature range is invalid, end: ´%s´ is lower than start: ´%s´", endTemperatureRange, startTemperatureRange));
        }

        // Check if the range is overlapping with any existing range in the database
        Long overlappingRanges = airTemperatureRuleRepository.countOverlappingRanges(startTemperatureRange, endTemperatureRange);
        if (overlappingRanges > 0) {
            throw new CustomBadRequestException("Provided air temperature range is overlapping with an existing range in the database");
        }
    }

    private ExtraFeeAirTemperatureRule patchValidateInputs(Long id, Double startTemperatureRange, Double endTemperatureRange, Double fee) {

        Optional<ExtraFeeAirTemperatureRule> rule = airTemperatureRuleRepository.findById(id);

        ExtraFeeAirTemperatureRule rule1 = rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee air temperature rule for this id: ´%s´ does not exist", id)));

        if (startTemperatureRange == null) {
            startTemperatureRange = rule1.getStartAirTemperatureRange();
        }
        if (endTemperatureRange == null) {
            endTemperatureRange = rule1.getEndAirTemperatureRange();
        }

        validateInputs(startTemperatureRange, endTemperatureRange, fee);

        return rule1;
    }

    public List<ExtraFeeAirTemperatureRule> getAllExtraFeeAirTemperatureRules() {
        List<ExtraFeeAirTemperatureRule> ruleList = airTemperatureRuleRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new CustomNotFoundException("No extra fee air temperature rules in the database.");
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

    public ExtraFeeAirTemperatureRule getExtraFeeAirTemperatureRuleById(Long id) {
        Optional<ExtraFeeAirTemperatureRule> rule = airTemperatureRuleRepository.findById(id);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee air temperature rule for this id: ´%s´ does not exist", id)));
    }

    public ExtraFeeAirTemperatureRule patchExtraFeeAirTemperatureRuleById(Long id, Double startTemperatureRange, Double endTemperatureRange, Double fee) {

        ExtraFeeAirTemperatureRule patchedRule = patchValidateInputs(id, startTemperatureRange, endTemperatureRange, fee);

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
            throw new CustomNotFoundException(String.format("Extra fee air temperature rule for this id: ´%s´ does not exist", id));
        }
    }

    public ExtraFeeAirTemperatureRule getByTemperature(double airTemperature) {
        Optional<ExtraFeeAirTemperatureRule> rule = airTemperatureRuleRepository.findByStartAirTemperatureRangeLessThanEqualAndEndAirTemperatureRangeGreaterThanEqual(airTemperature, airTemperature);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee air temperature rule for this temperature: ´%s´ does not exist", airTemperature)));
    }

}

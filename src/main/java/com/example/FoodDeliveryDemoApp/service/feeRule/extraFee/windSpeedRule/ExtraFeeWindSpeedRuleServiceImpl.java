package com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.windSpeedRule;

import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
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

    // todo add documentation

    private void validateRequiredInputs(Double startWindSpeedRange, Double endWindSpeedRange, Double fee) {
        if (startWindSpeedRange == null) {
            throw new CustomBadRequestException("Start of wind speed range must be provided");
        }
        if (endWindSpeedRange == null) {
            throw new CustomBadRequestException("End of wind speed range must be provided");
        }
        if (fee == null) {
            throw new CustomBadRequestException("Fee must be provided");
        }
    }

    private  void validateInputs(Double startWindSpeedRange, Double endWindSpeedRange, Double fee) {

        if (startWindSpeedRange != null && startWindSpeedRange < 0.0) {
            throw new CustomBadRequestException(String.format("Provided wind speed: ´%s´ is lower than zero.", startWindSpeedRange));
        }
        if (endWindSpeedRange != null && endWindSpeedRange < 0.0) {
            throw new CustomBadRequestException(String.format("Provided wind speed: ´%s´ is lower than zero.", endWindSpeedRange));
        }
        //noinspection ConstantConditions
        if (endWindSpeedRange < startWindSpeedRange) {
            throw new CustomBadRequestException(String.format("Provided wind speed range is invalid, end: ´%s´ is lower than start: ´%s´", endWindSpeedRange, startWindSpeedRange));
        }

        if (fee != null && fee < 0.0) {
            throw new CustomBadRequestException(String.format("Fee: ´%s´ must be positive", fee));
        }

        // Check if the range is overlapping with any existing range in the database
        Long overlappingRanges = windSpeedRuleRepository.countOverlappingRanges(startWindSpeedRange, endWindSpeedRange);
        if (overlappingRanges > 0) {
            throw new CustomBadRequestException("Provided wind speed range is overlapping with an existing range in the database");
        }

    }

    private ExtraFeeWindSpeedRule patchValidateInputs(Long id, Double startWindSpeedRange, Double endWindSpeedRange, Double fee) {

        Optional<ExtraFeeWindSpeedRule> rule = windSpeedRuleRepository.findById(id);

        ExtraFeeWindSpeedRule rule1 = rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee wind speed rule for this id: ´%s´ does not exist", id)));

        if (startWindSpeedRange == null) {
            startWindSpeedRange = rule1.getStartWindSpeedRange();
        }
        if (endWindSpeedRange == null) {
            endWindSpeedRange = rule1.getEndWindSpeedRange();
        }

        validateInputs(startWindSpeedRange, endWindSpeedRange, fee);

        return rule1;
    }

    public List<ExtraFeeWindSpeedRule> getAllExtraFeeWindSpeedRules() {
        List<ExtraFeeWindSpeedRule> ruleList = windSpeedRuleRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new CustomNotFoundException("No extra fee wind speed rules in the database.");
        } else {
            return ruleList;
        }
    }

    public ExtraFeeWindSpeedRule addExtraFeeWindSpeedRule(Double startWindSpeedRange, Double endWindSpeedRange, Double fee) {

        validateRequiredInputs(startWindSpeedRange, endWindSpeedRange, fee);
        validateInputs(startWindSpeedRange, endWindSpeedRange, fee);

        ExtraFeeWindSpeedRule rule = new ExtraFeeWindSpeedRule();

        rule.setStartWindSpeedRange(startWindSpeedRange);
        rule.setEndWindSpeedRange(endWindSpeedRange);
        rule.setFee(fee);

        return windSpeedRuleRepository.save(rule);
    }

    public ExtraFeeWindSpeedRule getExtraFeeWindSpeedRuleById(Long id) {
        Optional<ExtraFeeWindSpeedRule> rule = windSpeedRuleRepository.findById(id);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee wind speed rule for this id: ´%s´ does not exist", id)));
    }

    public ExtraFeeWindSpeedRule patchExtraFeeWindSpeedRuleById(Long id, Double startWindSpeedRange, Double endWindSpeedRange, Double fee) {

        ExtraFeeWindSpeedRule patchedRule = patchValidateInputs(id, startWindSpeedRange, endWindSpeedRange, fee);

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
            throw new CustomNotFoundException(String.format("Extra fee wind speed rule for this id: ´%s´ does not exist", id));
        }
    }

    public ExtraFeeWindSpeedRule getByWindSpeed(Double windSpeed) {
        Optional<ExtraFeeWindSpeedRule> rule = windSpeedRuleRepository.findByStartWindSpeedRangeLessThanEqualAndEndWindSpeedRangeGreaterThanEqual(windSpeed, windSpeed);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee wind speed rule for this wind speed: ´%s´ does not exist", windSpeed)));
    }

}

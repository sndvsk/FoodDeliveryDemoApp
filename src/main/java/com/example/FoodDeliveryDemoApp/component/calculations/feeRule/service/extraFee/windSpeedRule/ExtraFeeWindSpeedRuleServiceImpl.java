package com.example.FoodDeliveryDemoApp.component.calculations.feeRule.service.extraFee.windSpeedRule;

import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.repository.ExtraFeeWindSpeedRuleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ExtraFeeWindSpeedRuleServiceImpl implements ExtraFeeWindSpeedRuleService {

    private final ExtraFeeWindSpeedRuleRepository windSpeedRuleRepository;

    public ExtraFeeWindSpeedRuleServiceImpl(ExtraFeeWindSpeedRuleRepository windSpeedRuleRepository) {
        this.windSpeedRuleRepository = windSpeedRuleRepository;
    }

    /**
     * Validates the required inputs for adding a new ExtraFeeWindSpeedRule.
     *
     * @param startWindSpeedRange the start of the wind speed range
     * @param endWindSpeedRange the end of the wind speed range
     * @param fee the fee for the wind speed range
     * @throws CustomBadRequestException if any of the required inputs is not provided
     * @throws CustomNotFoundException if an ExtraFeeWindSpeedRule with the same start and
     * end wind speed range already exists in repository
     */
    private void validateRequiredInputs(Double startWindSpeedRange, Double endWindSpeedRange, Double fee)
            throws CustomBadRequestException, CustomNotFoundException {
        if (startWindSpeedRange == null) {
            throw new CustomBadRequestException("Start of wind speed range must be provided");
        }
        if (endWindSpeedRange == null) {
            throw new CustomBadRequestException("End of wind speed range must be provided");
        }
        if (fee == null) {
            throw new CustomBadRequestException("Fee must be provided");
        }

        boolean rule = doesWindSpeedRuleExist(startWindSpeedRange, endWindSpeedRange);
        if (rule) {
            throw new CustomNotFoundException(
                    String.format("Extra fee rule for this wind speed range: start: ´%s´ and end: ´%s´ already exists",
                            startWindSpeedRange, endWindSpeedRange));
        }

    }

    /**
     * Validates the inputs for adding or updating an ExtraFeeWindSpeedRule.
     *
     * @param startWindSpeedRange the start of the wind speed range
     * @param endWindSpeedRange the end of the wind speed range
     * @param fee the fee for the wind speed range
     * @throws CustomBadRequestException if the start or end wind speed range is lower than 0,
     * the end wind speed range is lower than the start wind speed range, or the fee is negative
     * @throws CustomBadRequestException if the provided wind speed range overlaps with
     * an existing range in the database
     * @throws CustomBadRequestException if an ExtraFeeWindSpeedRule with the same start,
     * end and fee already exists or the provided id does not match an existing ExtraFeeWindSpeedRule
     */
    private  void validateInputs(Double startWindSpeedRange, Double endWindSpeedRange, Double fee)
            throws CustomBadRequestException, CustomNotFoundException {

        if (startWindSpeedRange != null && startWindSpeedRange < 0.0) {
            throw new CustomBadRequestException(
                    String.format("Provided wind speed: ´%s´ is lower than zero.", startWindSpeedRange));
        }
        if (endWindSpeedRange != null && endWindSpeedRange < 0.0) {
            throw new CustomBadRequestException(
                    String.format("Provided wind speed: ´%s´ is lower than zero.", endWindSpeedRange));
        }
        //noinspection ConstantConditions
        if (endWindSpeedRange < startWindSpeedRange) {
            throw new CustomBadRequestException(
                    String.format("Provided wind speed range is invalid, end: ´%s´ is lower than start: ´%s´",
                            endWindSpeedRange, startWindSpeedRange));
        }

        if (fee != null && fee < 0.0) {
            throw new CustomBadRequestException(
                    String.format("Fee: ´%s´ must be positive", fee));
        }

        boolean rule = doesWindSpeedRuleWithThisFeeExist(startWindSpeedRange, endWindSpeedRange, fee);
        if (rule) {
            throw new CustomBadRequestException(
                    String.format("Extra fee rule for this wind speed range: start: ´%s´, end: ´%s´ and fee: ´%s´ " +
                            "already exists", startWindSpeedRange, endWindSpeedRange, fee));
        }

    }

    /**
     * This method validates the inputs for patching an extra fee wind speed rule.
     *
     * @param id the id of the extra fee wind speed rule to patch
     * @param startWindSpeedRange the new start wind speed range, null if it should not be updated
     * @param endWindSpeedRange the new end wind speed range, null if it should not be updated
     * @param fee the new fee, null if it should not be updated
     * @return the extra fee wind speed rule to patch
     * @throws CustomNotFoundException if the extra fee wind speed rule for the given id does not exist
     */
    private ExtraFeeWindSpeedRule patchValidateInputs(
            Long id, Double startWindSpeedRange, Double endWindSpeedRange, Double fee) throws CustomNotFoundException {

        Optional<ExtraFeeWindSpeedRule> rule = windSpeedRuleRepository.findById(id);

        ExtraFeeWindSpeedRule rule1 = rule
                .orElseThrow(() -> new CustomNotFoundException(
                        String.format("Extra fee wind speed rule for this id: ´%s´ does not exist", id)));

        if (startWindSpeedRange == null) {
            startWindSpeedRange = rule1.getStartWindSpeedRange();
        }
        if (endWindSpeedRange == null) {
            endWindSpeedRange = rule1.getEndWindSpeedRange();
        }

        validateInputs(startWindSpeedRange, endWindSpeedRange, fee);

        return rule1;
    }

    /**
     * Validates and checks if the wind speed range overlaps with any existing range in the database.
     *
     * @param startWindSpeedRange the start value of the wind speed range to be added.
     * @param endWindSpeedRange the end value of the wind speed range to be added
     * @throws CustomBadRequestException if the wind speed range overlaps with an existing range in the database
     */
    private void addValidateInputs(Double startWindSpeedRange, Double endWindSpeedRange) {
        // Check if the range is overlapping with any existing range in the database
        Long overlappingRanges = windSpeedRuleRepository.countOverlappingRanges(startWindSpeedRange, endWindSpeedRange);
        if (overlappingRanges > 0) {
            throw new CustomBadRequestException(
                    "Provided wind speed range is overlapping with an existing range in the database");
        }

        Long overlappingInsideRanges = windSpeedRuleRepository.countInsideRange(startWindSpeedRange, endWindSpeedRange);
        if (overlappingInsideRanges > 0) {
            throw new CustomBadRequestException(
                    "Provided wind speed range is overlapping with an existing range in the database");
        }
    }

    /**
     * This method checks if an extra fee wind speed rule with the given start and end wind speed range exists.
     *
     * @param start the start wind speed range
     * @param end the end wind speed range
     * @return true if the extra fee wind speed rule exists, false otherwise
     */
    private boolean doesWindSpeedRuleExist(Double start, Double end) {
        Optional<ExtraFeeWindSpeedRule> rule =
                windSpeedRuleRepository.findByStartWindSpeedRangeAndEndWindSpeedRange(start, end);
        return rule.isPresent();
    }

    /**
     * This method checks if an extra fee wind speed rule with the given start and end wind speed range and fee exists.
     *
     * @param start the start wind speed range
     * @param end the end wind speed range
     * @param fee the fee
     * @return true if the extra fee wind speed rule exists, false otherwise
     */
    private boolean doesWindSpeedRuleWithThisFeeExist(Double start, Double end, Double fee) {
        Optional<ExtraFeeWindSpeedRule> rule =
                windSpeedRuleRepository.findByStartWindSpeedRangeAndEndWindSpeedRangeAndFee(start, end, fee);
        return rule.isPresent();
    }

    /**
     * This method gets all the extra fee wind speed rules.
     *
     * @return a list of all the extra fee wind speed rules
     * @throws CustomNotFoundException if there are no extra fee wind speed rules in the database
     */
    public List<ExtraFeeWindSpeedRule> getAllExtraFeeWindSpeedRules() throws CustomNotFoundException {
        List<ExtraFeeWindSpeedRule> ruleList = windSpeedRuleRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new CustomNotFoundException("No extra fee wind speed rules in the database.");
        } else {
            return ruleList;
        }
    }

    /**
     * This method adds an extra fee wind speed rule.
     *
     * @param startWindSpeedRange the start wind speed range
     * @param endWindSpeedRange the end wind speed range
     * @param fee the fee
     * @return the added extra fee wind speed rule
     */
    public ExtraFeeWindSpeedRule addExtraFeeWindSpeedRule(
            Double startWindSpeedRange, Double endWindSpeedRange, Double fee) {

        validateRequiredInputs(startWindSpeedRange, endWindSpeedRange, fee);
        validateInputs(startWindSpeedRange, endWindSpeedRange, fee);
        addValidateInputs(startWindSpeedRange, endWindSpeedRange);

        ExtraFeeWindSpeedRule rule = new ExtraFeeWindSpeedRule();

        rule.setStartWindSpeedRange(startWindSpeedRange);
        rule.setEndWindSpeedRange(endWindSpeedRange);
        rule.setFee(fee);

        return windSpeedRuleRepository.save(rule);
    }

    /**
     * Retrieves the ExtraFeeWindSpeedRule with the given id from the database.
     *
     * @param id the id of the ExtraFeeWindSpeedRule to retrieve
     * @return the ExtraFeeWindSpeedRule with the given id
     * @throws CustomNotFoundException if the ExtraFeeWindSpeedRule with the given id does not exist
     */
    public ExtraFeeWindSpeedRule getExtraFeeWindSpeedRuleById(Long id) throws  CustomNotFoundException {
        Optional<ExtraFeeWindSpeedRule> rule = windSpeedRuleRepository.findById(id);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(
                        String.format("Extra fee wind speed rule for this id: ´%s´ does not exist", id)));
    }

    /**
     * Updates the specified fields of the ExtraFeeWindSpeedRule with the given id in the database.
     *
     * @param id the id of the ExtraFeeWindSpeedRule to update
     * @param startWindSpeedRange the new start wind speed range to set for the ExtraFeeWindSpeedRule,
     *                            or null to leave it unchanged
     * @param endWindSpeedRange the new end wind speed range to set for the ExtraFeeWindSpeedRule,
     *                          or null to leave it unchanged
     * @param fee the new fee to set for the ExtraFeeWindSpeedRule, or null to leave it unchanged
     * @return the updated ExtraFeeWindSpeedRule object
     */
    public ExtraFeeWindSpeedRule patchExtraFeeWindSpeedRuleById(
            Long id, Double startWindSpeedRange, Double endWindSpeedRange, Double fee) {
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

    /**
     * Deletes the ExtraFeeWindSpeedRule with the given ID from the database.
     *
     * @param id the id of the ExtraFeeWindSpeedRule to delete
     * @return a message indicating that the ExtraFeeWindSpeedRule was successfully deleted
     * @throws CustomNotFoundException if the ExtraFeeWindSpeedRule with the given id does not exist
     */
    public String deleteExtraFeeWindSpeedRuleById(Long id) throws CustomNotFoundException {
        if (windSpeedRuleRepository.existsById(id)) {
            windSpeedRuleRepository.deleteById(id);
            return String.format("Extra fee wind speed rule with id: ´%s´ was deleted", id);
        } else {
            throw new CustomNotFoundException(
                    String.format("Extra fee wind speed rule for this id: ´%s´ does not exist", id));
        }
    }

    /**
     * Retrieves the ExtraFeeWindSpeedRule that applies to the given wind speed from the database.
     *
     * @param windSpeed the wind speed to retrieve the ExtraFeeWindSpeedRule for
     * @return the ExtraFeeWindSpeedRule that applies to the given wind speed
     * @throws CustomNotFoundException if no ExtraFeeWindSpeedRule applies to the given wind speed
     */
    public ExtraFeeWindSpeedRule getByWindSpeed(Double windSpeed) throws CustomNotFoundException {
        Optional<ExtraFeeWindSpeedRule> rule =
                windSpeedRuleRepository.findByStartWindSpeedRangeLessThanEqualAndEndWindSpeedRangeGreaterThanEqual(
                        windSpeed, windSpeed);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(
                        String.format("Extra fee wind speed rule for this wind speed: ´%s´ does not exist", windSpeed)));
    }

}

package com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.airTemperatureRule;

import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
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

    /**
     * This method validates the required inputs for adding an extra fee air temperature rule. It throws a CustomBadRequestException if any of the required inputs is null.
     *
     * @param startTemperatureRange start of air temperature range
     * @param endTemperatureRange end of air temperature range
     * @param fee fee to be charged for the extra fee air temperature rule
     * @throws CustomBadRequestException if the required inputs are not provided
     * @throws CustomNotFoundException if there is already an existing rule for the given air temperature range
     */
    private void validateRequiredInputs(Double startTemperatureRange, Double endTemperatureRange, Double fee) throws CustomBadRequestException, CustomNotFoundException{
        if (startTemperatureRange == null) {
            throw new CustomBadRequestException("Start of air temperature range must be provided");
        }
        if (endTemperatureRange == null) {
            throw new CustomBadRequestException("End of air temperature range must be provided");
        }
        if (fee == null) {
            throw new CustomBadRequestException("Fee must be provided");
        }

        boolean rule = doesAirTemperatureRuleExist(startTemperatureRange, endTemperatureRange);
        if (rule) {
            throw new CustomNotFoundException(String.format("Extra fee rule for this air temperature range: start: ´%s´ and end: ´%s´ already exists", startTemperatureRange, endTemperatureRange));
        }

    }

    /**
     * This method validates the inputs for an extra fee air temperature rule.
     * It checks if the provided fee is positive, the provided air temperatures are above absolute zero and the end of the range is not lower than the start of the range.
     *
     * @param startTemperatureRange start of air temperature range
     * @param endTemperatureRange end of air temperature range
     * @param fee fee to be charged for the extra fee air temperature rule
     * @throws CustomBadRequestException if fee is not positive
     * @throws CustomBadRequestException if air temperature is lower than absolute zero
     * @throws CustomBadRequestException if there is already an existing rule for the given air temperature range with the provided fee
     */
    private void validateInputs(Double startTemperatureRange, Double endTemperatureRange, Double fee) throws  CustomBadRequestException {

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
        if (Math.abs(endTemperatureRange) < Math.abs(startTemperatureRange)) {
            throw new CustomBadRequestException(String.format("Provided air temperature range is invalid, end: ´%s´ is lower than start: ´%s´", endTemperatureRange, startTemperatureRange));
        }

        boolean rule = doesAirTemperatureRuleWithThisFeeExist(startTemperatureRange, endTemperatureRange, fee);
        if (rule) {
            throw new CustomBadRequestException(String.format("Extra fee rule for this air temperature range: start: ´%s´, end: ´%s´ and fee: ´%s´ already exists", startTemperatureRange, endTemperatureRange, fee));
        }

    }

    /**
     * This method validates the inputs for patching an existing extra fee air temperature rule.
     * It checks if the provided air temperatures are above absolute zero and the end of the range is not lower than the start of the range.
     *
     * @param id the ID of the extra fee air temperature rule to be patched
     * @param startTemperatureRange start of air temperature range
     * @param endTemperatureRange end of air temperature range
     * @param fee fee to be charged for the extra fee air temperature rule
     * @return ExtraFeeAirTemperatureRule the extra fee air temperature rule with the given id
     * @throws CustomNotFoundException if there is no existing rule for the given id
     */
    private ExtraFeeAirTemperatureRule patchValidateInputs(Long id, Double startTemperatureRange, Double endTemperatureRange, Double fee) throws CustomNotFoundException {

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


    /**
     * Validates the input range for air temperature rules to ensure it does not overlap with an existing range in the database.
     *
     * Throws a CustomBadRequestException with a message if an overlapping range exists in the database
     * @param startTemperatureRange the start of the input temperature range
     * @param endTemperatureRange the end of the input temperature range
     * @throws CustomBadRequestException if the provided air temperature range is overlapping with any existing range in the database
     */
    private void addValidateInputs(Double startTemperatureRange, Double endTemperatureRange) {
        // Check if the range is overlapping with any existing range in the database
        Long overlappingRanges = airTemperatureRuleRepository.countOverlappingRanges(startTemperatureRange, endTemperatureRange);
        if (overlappingRanges > 0) {
            throw new CustomBadRequestException("Provided air temperature range is overlapping with an existing range in the database");
        }
    }

    /**
     * This method checks if there is already an existing extra fee air temperature rule for the given air temperature range.
     *
     * @param start start of air temperature range
     * @param end end of air temperature range
     * @return boolean true if there is an existing extra fee air temperature rule, false otherwise
     */
    private boolean doesAirTemperatureRuleExist(Double start, Double end) {
        Optional<ExtraFeeAirTemperatureRule> rule = airTemperatureRuleRepository.findByStartAirTemperatureRangeAndEndAirTemperatureRange(start, end);
        return rule.isPresent();
    }

    /**
     * This method checks if there is already an existing extra fee air temperature rule for the given air temperature range and fee.
     *
     * @param start start of air temperature range
     * @param end end of air temperature range
     * @param fee fee to be charged for the extra fee air temperature rule
     * @return boolean true if there is an existing extra fee air temperature rule with the given fee, false otherwise
     */
    private boolean doesAirTemperatureRuleWithThisFeeExist(Double start, Double end, Double fee) {
        Optional<ExtraFeeAirTemperatureRule> rule = airTemperatureRuleRepository.findByStartAirTemperatureRangeAndEndAirTemperatureRangeAndFee(start, end, fee);
        return rule.isPresent();
    }

    /**
     * Retrieves all ExtraFeeAirTemperatureRule objects from the database.
     *
     * @return List of ExtraFeeAirTemperatureRule objects.
     * @throws CustomNotFoundException if no extra fee air temperature rules exist in the database.
     */
    public List<ExtraFeeAirTemperatureRule> getAllExtraFeeAirTemperatureRules() throws CustomNotFoundException {
        List<ExtraFeeAirTemperatureRule> ruleList = airTemperatureRuleRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new CustomNotFoundException("No extra fee air temperature rules in the database.");
        } else {
            return ruleList;
        }
    }

    /**
     * Adds an ExtraFeeAirTemperatureRule object to the database.
     *
     * @param startTemperatureRange Double value for the start of the temperature range.
     * @param endTemperatureRange Double value for the end of the temperature range.
     * @param fee Double value for the fee.
     * @return the saved ExtraFeeAirTemperatureRule object.
     */
    public ExtraFeeAirTemperatureRule addExtraFeeAirTemperatureRule(Double startTemperatureRange, Double endTemperatureRange, Double fee) {

        validateRequiredInputs(startTemperatureRange, endTemperatureRange, fee);
        validateInputs(startTemperatureRange, endTemperatureRange, fee);
        addValidateInputs(startTemperatureRange, endTemperatureRange);

        ExtraFeeAirTemperatureRule rule = new ExtraFeeAirTemperatureRule();

        rule.setStartAirTemperatureRange(startTemperatureRange);
        rule.setEndAirTemperatureRange(endTemperatureRange);
        rule.setFee(fee);

        return airTemperatureRuleRepository.save(rule);
    }

    /**
     * Retrieves an ExtraFeeAirTemperatureRule object by its id.
     *
     * @param id the id of the ExtraFeeAirTemperatureRule object to retrieve.
     * @return the retrieved ExtraFeeAirTemperatureRule object.
     * @throws CustomNotFoundException if the ExtraFeeAirTemperatureRule object with the given id does not exist in the database.
     */
    public ExtraFeeAirTemperatureRule getExtraFeeAirTemperatureRuleById(Long id) throws CustomNotFoundException {
        Optional<ExtraFeeAirTemperatureRule> rule = airTemperatureRuleRepository.findById(id);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee air temperature rule for this id: ´%s´ does not exist", id)));
    }

    /**
     * Updates an ExtraFeeAirTemperatureRule object by its id with the provided data.
     *
     * @param id the id of the ExtraFeeAirTemperatureRule object to update
     * @param startTemperatureRange Double value for the start of the temperature range
     * @param endTemperatureRange Double value for the end of the temperature range
     * @param fee Double value for the fee
     * @return the updated ExtraFeeAirTemperatureRule object
     */
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

    /**
     * Deletes an ExtraFeeAirTemperatureRule object by its id.
     *
     * @param id the id of the ExtraFeeAirTemperatureRule object to delete
     * @return a String message indicating that the ExtraFeeAirTemperatureRule object was successfully deleted
     * @throws CustomNotFoundException if the ExtraFeeAirTemperatureRule object with the given id does not exist in the database
     */
    public String deleteExtraFeeAirTemperatureRuleById(Long id) throws CustomNotFoundException {
        if (airTemperatureRuleRepository.existsById(id)) {
            airTemperatureRuleRepository.deleteById(id);
            return String.format("Extra fee air temperature rule with id: ´%s´ was deleted", id);
        } else {
            throw new CustomNotFoundException(String.format("Extra fee air temperature rule for this id: ´%s´ does not exist", id));
        }
    }

    /**
     * Retrieves an ExtraFeeAirTemperatureRule object based on the given air temperature value.
     *
     * @param airTemperature Double value for the air temperature to search for
     * @return the retrieved ExtraFeeAirTemperatureRule object
     * @throws CustomNotFoundException if no ExtraFeeAirTemperatureRule object exists in the database for the given temperature
     */
    public ExtraFeeAirTemperatureRule getByTemperature(double airTemperature) throws CustomNotFoundException {
        Optional<ExtraFeeAirTemperatureRule> rule = airTemperatureRuleRepository.findByStartAirTemperatureRangeLessThanEqualAndEndAirTemperatureRangeGreaterThanEqual(airTemperature, airTemperature);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee air temperature rule for this temperature: ´%s´ does not exist", airTemperature)));
    }

}

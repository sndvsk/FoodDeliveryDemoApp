package com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.weatherPhenomenonRule;

import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.repository.rules.ExtraFeeWeatherPhenomenonRuleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ExtraFeeWeatherPhenomenonRuleServiceImpl implements ExtraFeeWeatherPhenomenonRuleService {

    private final ExtraFeeWeatherPhenomenonRuleRepository weatherPhenomenonRepository;

    protected ExtraFeeWeatherPhenomenonRuleServiceImpl(ExtraFeeWeatherPhenomenonRuleRepository weatherPhenomenonRuleRepository) {
        this.weatherPhenomenonRepository = weatherPhenomenonRuleRepository;
    }

    /**
     * Validates if the required inputs are not null and if a rule for the provided weather phenomenon name doesn't already exist.
     *
     * @param weatherPhenomenonName a String representing the weather phenomenon name to be validated.
     * @param fee a Double representing the fee to be validated.
     * @throws CustomBadRequestException if any of the required inputs is null
     * @throws CustomBadRequestException if a rule for the provided weather phenomenon name already exists.
     */
    private void validateRequiredInputs(String weatherPhenomenonName, Double fee) throws CustomBadRequestException, CustomNotFoundException {
        if (weatherPhenomenonName == null) {
            throw new CustomBadRequestException("Weather phenomenon name must be provided");
        }

        if (fee == null) {
            throw new CustomBadRequestException("Fee must be provided");
        }

        boolean rule = doesWeatherPhenomenonRuleExist(weatherPhenomenonName);
        if (rule) {
            throw new CustomNotFoundException(String.format("Extra fee rule for this: ´%s´ weather phenomenon already exists", weatherPhenomenonName));
        }

    }

    /**
     * Validates if the provided weather phenomenon name contains only letters and if the provided fee is positive. Also, checks if a rule for the provided weather phenomenon name and fee doesn't already exist.
     *
     * @param weatherPhenomenon a String representing the weather phenomenon name to be validated
     * @param fee a Double representing the fee to be validated
     * @throws CustomBadRequestException if the provided weather phenomenon name doesn't contain only letters
     * @throws CustomBadRequestException if the provided fee is not positive
     * @throws CustomBadRequestException if a rule for the provided weather phenomenon name and fee already exists
     */
    private void validateInputs(String weatherPhenomenon, Double fee) throws CustomBadRequestException, CustomNotFoundException {

        if (weatherPhenomenon != null) {
            weatherPhenomenon = weatherPhenomenon.replaceAll("\u00A0", "").replaceAll("\\u00A0", "").replaceAll(" ", "");
            if (!weatherPhenomenon.chars().allMatch(Character::isLetter)) {
                throw new CustomBadRequestException(String.format("Weather phenomenon name: ´%s´ must contain only letters", weatherPhenomenon));
            }
        }

        if (fee != null && fee < 0.0) {
            throw new CustomBadRequestException(String.format("Fee: ´%s´ must be positive", fee));
        }

        boolean rule = doesWeatherPhenomenonRuleWithThisFeeExist(weatherPhenomenon, fee);
        if (rule) {
            throw new CustomBadRequestException(String.format("Extra fee rule for this: ´%s´ weather phenomenon and fee: ´%s´ already exists", weatherPhenomenon, fee));
        }

    }

    /**
     * Verifies if a rule for a provided weather phenomenon name exists in the repository.
     *
     * @param weatherPhenomenonName a String representing the weather phenomenon name to be checked
     * @return boolean indicating if a rule for the provided weather phenomenon name exists in the repository or not
     */
    private boolean doesWeatherPhenomenonRuleExist(String weatherPhenomenonName) {
        Optional<ExtraFeeWeatherPhenomenonRule> rule = weatherPhenomenonRepository.findByWeatherPhenomenonName(weatherPhenomenonName);
        return rule.isPresent();
    }

    /**
     * Verifies if a rule for a provided weather phenomenon name and fee exists in the repository.
     *
     * @param weatherPhenomenon a String representing the weather phenomenon name to be checked
     * @param fee a Double representing the fee to be checked
     * @return a boolean indicating if a rule for the provided weather phenomenon name and fee exists in the repository or not
     */
    private boolean doesWeatherPhenomenonRuleWithThisFeeExist(String weatherPhenomenon, Double fee) {
        Optional<ExtraFeeWeatherPhenomenonRule> rule = weatherPhenomenonRepository.findByWeatherPhenomenonNameAndFee(weatherPhenomenon, fee);
        return rule.isPresent();
    }

    /**
     * Retrieves all extra fee weather phenomenon rules from the repository.
     *
     * @return a List of ExtraFeeWeatherPhenomenonRule objects representing all the rules in the repository
     * @throws CustomNotFoundException if no extra fee weather phenomenon rules exist in the repository
     */
    public List<ExtraFeeWeatherPhenomenonRule> getAllExtraFeeWeatherPhenomenonRules() throws CustomNotFoundException {
        List<ExtraFeeWeatherPhenomenonRule> ruleList = weatherPhenomenonRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new CustomNotFoundException("No extra fee weather phenomenon rules in the database.");
        } else {
            return ruleList;
        }
    }

    /**
     * Adds a new extra fee weather phenomenon rule to the repository.
     *
     * @param weatherPhenomenonName a String representing the weather phenomenon name to be added
     * @param fee a Double representing the fee to be added
     * @return an ExtraFeeWeatherPhenomenonRule object representing the newly added rule
     */
    public ExtraFeeWeatherPhenomenonRule addExtraFeeWeatherPhenomenonRule(String weatherPhenomenonName, Double fee) {
        validateRequiredInputs(weatherPhenomenonName, fee);
        validateInputs(weatherPhenomenonName, fee);

        ExtraFeeWeatherPhenomenonRule rule = new ExtraFeeWeatherPhenomenonRule();

        rule.setWeatherPhenomenonName(weatherPhenomenonName);
        rule.setFee(fee);

        return weatherPhenomenonRepository.save(rule);
    }

    /**
     * Retrieves an extra fee weather phenomenon rule from the repository based on its id.
     *
     * @param id a Long representing the id of the rule to be retrieved
     * @return an ExtraFeeWeatherPhenomenonRule object representing the retrieved rule
     * @throws CustomNotFoundException if no rule with the provided id exists in the repository
     */
    public ExtraFeeWeatherPhenomenonRule getExtraFeeWeatherPhenomenonRuleById(Long id) throws CustomNotFoundException {
        Optional<ExtraFeeWeatherPhenomenonRule> rule = weatherPhenomenonRepository.findById(id);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee weather phenomenon rule for this id: ´%s´ does not exist", id)));
    }

    /**
     * Updates ExtraFeeWeatherPhenomenonRule by Id with given weather phenomenon name and fee
     *
     * @param id id of the ExtraFeeWeatherPhenomenonRule to be updated
     * @param weatherPhenomenonName name of the weather phenomenon to be updated
     * @param fee fee amount to be updated
     * @return the updated ExtraFeeWeatherPhenomenonRule object
     * @throws CustomNotFoundException if no rule with the provided id exists in repository
     */
    public ExtraFeeWeatherPhenomenonRule patchExtraFeeWeatherPhenomenonRuleById(Long id, String weatherPhenomenonName, Double fee) throws CustomNotFoundException {

        validateInputs(weatherPhenomenonName, fee);

        Optional<ExtraFeeWeatherPhenomenonRule> rule = weatherPhenomenonRepository.findById(id);

        ExtraFeeWeatherPhenomenonRule patchedRule = rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee weather phenomenon rule for this id: ´%s´ does not exist", id)));

        if (weatherPhenomenonName != null) {
            patchedRule.weatherPhenomenonName = weatherPhenomenonName;
        }
        if (fee != null) {
            patchedRule.fee = fee;
        }

        return weatherPhenomenonRepository.save(patchedRule);
    }

    /**
     * Deletes ExtraFeeWeatherPhenomenonRule by id.
     *
     * @param id id of the ExtraFeeWeatherPhenomenonRule to be deleted
     * @return Returns a message with the deleted ExtraFeeWeatherPhenomenonRule id
     * @throws CustomNotFoundException if no rule with the provided id exists in repository
     */
    public String deleteExtraFeeWeatherPhenomenonRuleById(Long id) throws CustomNotFoundException {
        if (weatherPhenomenonRepository.existsById(id)) {
            weatherPhenomenonRepository.deleteById(id);
            return String.format("Extra fee weather phenomenon rule with id: ´%s´ was deleted", id);
        } else {
            throw new CustomNotFoundException(String.format("Extra fee weather phenomenon rule for this id: ´%s´ does not exist", id));
        }
    }

    /**
     * Retrieves ExtraFeeWeatherPhenomenonRule by Weather Phenomenon name
     *
     * @param weatherPhenomenon name of the weather phenomenon to retrieve the ExtraFeeWeatherPhenomenonRule for
     * @return the ExtraFeeWeatherPhenomenonRule object for the given Weather Phenomenon name
     * @throws CustomNotFoundException if no ExtraFeeWeatherPhenomenonRule exists for given weather phenomenon name
     */
    public ExtraFeeWeatherPhenomenonRule getByWeatherPhenomenonName(String weatherPhenomenon) throws CustomNotFoundException {
        Optional<ExtraFeeWeatherPhenomenonRule> rule = weatherPhenomenonRepository.findByWeatherPhenomenonName(weatherPhenomenon);
        return rule.
                orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee rule for this: ´%s´ weather phenomenon does not exist", weatherPhenomenon)));
    }

}

package com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.weatherPhenomenonRule;

import com.example.FoodDeliveryDemoApp.exception.feeRule.FeeRuleBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.feeRule.FeeRuleNotFoundException;
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

    public List<ExtraFeeWeatherPhenomenonRule> getAllExtraFeeWeatherPhenomenonRules() {
        List<ExtraFeeWeatherPhenomenonRule> ruleList = weatherPhenomenonRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new FeeRuleNotFoundException("No extra fee weather phenomenon rules in the database.");
        } else {
            return ruleList;
        }
    }

    public ExtraFeeWeatherPhenomenonRule addExtraFeeWeatherPhenomenonRule(String weatherPhenomenonName, Double fee) {

        validateRequiredInputs(weatherPhenomenonName, fee);
        validateInputs(weatherPhenomenonName);

        ExtraFeeWeatherPhenomenonRule rule = new ExtraFeeWeatherPhenomenonRule();

        rule.setWeatherPhenomenonName(weatherPhenomenonName);
        rule.setFee(fee);

        return weatherPhenomenonRepository.save(rule);
    }

    private void validateRequiredInputs(String weatherPhenomenonName, Double fee) {
        if (weatherPhenomenonName == null) {
            throw new FeeRuleBadRequestException("Weather phenomenon name must be provided");
        }
        if (fee == null) {
            throw new FeeRuleBadRequestException("Fee must be provided");
        }
    }

    private void validateInputs(String weatherPhenomenonName) {
        if (weatherPhenomenonName != null && !weatherPhenomenonName.chars().allMatch(Character::isLetter)) {
            throw new FeeRuleBadRequestException(String.format("Weather phenomenon name: ´%s´ must contain only letters", weatherPhenomenonName));
        }
    }

    public ExtraFeeWeatherPhenomenonRule getExtraFeeWeatherPhenomenonRuleById(Long id) {
        Optional<ExtraFeeWeatherPhenomenonRule> rule = weatherPhenomenonRepository.findById(id);
        return rule
                .orElseThrow(() -> new FeeRuleNotFoundException(String.format("Extra fee weather phenomenon rule for this id: ´%s´ does not exist", id)));
    }

    public ExtraFeeWeatherPhenomenonRule patchExtraFeeWeatherPhenomenonRuleById(Long id, String weatherPhenomenonName, Double fee) {

        validateInputs(weatherPhenomenonName);

        Optional<ExtraFeeWeatherPhenomenonRule> rule = weatherPhenomenonRepository.findById(id);

        ExtraFeeWeatherPhenomenonRule patchedRule = rule
                .orElseThrow(() -> new FeeRuleNotFoundException(String.format("Extra fee weather phenomenon rule for this id: ´%s´ does not exist", id)));

        if (weatherPhenomenonName != null) {
            patchedRule.weatherPhenomenonName = weatherPhenomenonName;
        }
        if (fee != null) {
            patchedRule.fee = fee;
        }

        return weatherPhenomenonRepository.save(patchedRule);
    }

    public String deleteExtraFeeWeatherPhenomenonRuleById(Long id) {
        if (weatherPhenomenonRepository.existsById(id)) {
            weatherPhenomenonRepository.deleteById(id);
            return String.format("Extra fee weather phenomenon rule with id: ´%s´ was deleted", id);
        } else {
            throw new FeeRuleNotFoundException(String.format("Extra fee weather phenomenon rule for this id: ´%s´ does not exist", id));
        }
    }

    public ExtraFeeWeatherPhenomenonRule findByWeatherPhenomenonName(String weatherPhenomenonName) {
        Optional<ExtraFeeWeatherPhenomenonRule> weatherPhenomenon = weatherPhenomenonRepository.findByWeatherPhenomenonName(weatherPhenomenonName);
        return weatherPhenomenon.
                orElseThrow(() -> new FeeRuleNotFoundException(String.format("Extra fee rule for this: ´%s´ weather phenomenon does not exist", weatherPhenomenonName)));
    }

}

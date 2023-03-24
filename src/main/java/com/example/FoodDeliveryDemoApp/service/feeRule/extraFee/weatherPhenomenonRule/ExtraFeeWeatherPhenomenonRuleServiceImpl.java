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

    private void validateRequiredInputs(String weatherPhenomenonName, Double fee) {
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

    private void validateInputs(String weatherPhenomenon, Double fee) {

        if (weatherPhenomenon != null && !weatherPhenomenon.chars().allMatch(Character::isLetter)) {
            throw new CustomBadRequestException(String.format("Weather phenomenon name: ´%s´ must contain only letters", weatherPhenomenon));
        }

        if (fee != null && fee < 0.0) {
            throw new CustomBadRequestException(String.format("Fee: ´%s´ must be positive", fee));
        }

        boolean rule = doesWeatherPhenomenonRuleWithThisFeeExist(weatherPhenomenon, fee);
        if (rule) {
            throw new CustomNotFoundException(String.format("Extra fee rule for this: ´%s´ weather phenomenon and fee: ´%s´ already exists", weatherPhenomenon, fee));
        }

    }

    private boolean doesWeatherPhenomenonRuleExist(String weatherPhenomenonName) {
        Optional<ExtraFeeWeatherPhenomenonRule> rule = weatherPhenomenonRepository.findByWeatherPhenomenonName(weatherPhenomenonName);
        return rule.isPresent();
    }

    private boolean doesWeatherPhenomenonRuleWithThisFeeExist(String weatherPhenomenon, Double fee) {
        Optional<ExtraFeeWeatherPhenomenonRule> rule = weatherPhenomenonRepository.findByWeatherPhenomenonNameAndFee(weatherPhenomenon, fee);
        return rule.isPresent();
    }

    public List<ExtraFeeWeatherPhenomenonRule> getAllExtraFeeWeatherPhenomenonRules() {
        List<ExtraFeeWeatherPhenomenonRule> ruleList = weatherPhenomenonRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new CustomNotFoundException("No extra fee weather phenomenon rules in the database.");
        } else {
            return ruleList;
        }
    }

    public ExtraFeeWeatherPhenomenonRule addExtraFeeWeatherPhenomenonRule(String weatherPhenomenonName, Double fee) {

        validateRequiredInputs(weatherPhenomenonName, fee);
        validateInputs(weatherPhenomenonName, fee);

        ExtraFeeWeatherPhenomenonRule rule = new ExtraFeeWeatherPhenomenonRule();

        rule.setWeatherPhenomenonName(weatherPhenomenonName);
        rule.setFee(fee);

        return weatherPhenomenonRepository.save(rule);
    }

    public ExtraFeeWeatherPhenomenonRule getExtraFeeWeatherPhenomenonRuleById(Long id) {
        Optional<ExtraFeeWeatherPhenomenonRule> rule = weatherPhenomenonRepository.findById(id);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee weather phenomenon rule for this id: ´%s´ does not exist", id)));
    }

    public ExtraFeeWeatherPhenomenonRule patchExtraFeeWeatherPhenomenonRuleById(Long id, String weatherPhenomenonName, Double fee) {

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

    public String deleteExtraFeeWeatherPhenomenonRuleById(Long id) {
        if (weatherPhenomenonRepository.existsById(id)) {
            weatherPhenomenonRepository.deleteById(id);
            return String.format("Extra fee weather phenomenon rule with id: ´%s´ was deleted", id);
        } else {
            throw new CustomNotFoundException(String.format("Extra fee weather phenomenon rule for this id: ´%s´ does not exist", id));
        }
    }

    public ExtraFeeWeatherPhenomenonRule getByWeatherPhenomenonName(String weatherPhenomenon) {
        Optional<ExtraFeeWeatherPhenomenonRule> rule = weatherPhenomenonRepository.findByWeatherPhenomenonName(weatherPhenomenon);
        return rule.
                orElseThrow(() -> new CustomNotFoundException(String.format("Extra fee rule for this: ´%s´ weather phenomenon does not exist", weatherPhenomenon)));
    }

}

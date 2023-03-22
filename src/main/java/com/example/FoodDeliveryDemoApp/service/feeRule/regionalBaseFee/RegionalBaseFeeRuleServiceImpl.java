package com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee;

import com.example.FoodDeliveryDemoApp.exception.feeRule.FeeRuleBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.feeRule.FeeRuleNotFoundException;
import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import com.example.FoodDeliveryDemoApp.repository.rules.RegionalBaseFeeRuleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class RegionalBaseFeeRuleServiceImpl implements RegionalBaseFeeRuleService {

    private final RegionalBaseFeeRuleRepository baseFeeRuleRepository;

    public RegionalBaseFeeRuleServiceImpl(RegionalBaseFeeRuleRepository baseFeeRuleRepository) {
        this.baseFeeRuleRepository = baseFeeRuleRepository;
    }

    public List<RegionalBaseFeeRule> getAllRegionalBaseFeeRules() {
        List<RegionalBaseFeeRule> ruleList = baseFeeRuleRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new FeeRuleNotFoundException("No regional base fee rules in the database.");
        } else {
            return ruleList;
        }
    }

    public RegionalBaseFeeRule addBaseFeeRule(String city, String vehicleType, Double fee) {

        city = city.trim().toLowerCase(Locale.ROOT);
        vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);

        validateRequiredInputs(city, vehicleType, fee);
        validateInputs(city, vehicleType, fee);

        RegionalBaseFeeRule rule = new RegionalBaseFeeRule();

        rule.setCity(city);
        rule.setVehicleType(vehicleType);
        rule.setFee(fee);

        return baseFeeRuleRepository.save(rule);
    }

    public RegionalBaseFeeRule getRegionalBaseFeeRuleById(Long id) {
        Optional<RegionalBaseFeeRule> rule = baseFeeRuleRepository.findById(id);
        return rule
                .orElseThrow(() -> new FeeRuleNotFoundException(String.format("Regional base fee rule for this id: ´%s´ does not exist", id)));
    }

    public RegionalBaseFeeRule patchRegionalBaseFeeRuleById(Long id, String city, String vehicleType, Double fee) {

        validateInputs(city, vehicleType, fee);

        Optional<RegionalBaseFeeRule> rule = baseFeeRuleRepository.findById(id);

        RegionalBaseFeeRule patchedRule = rule
                .orElseThrow(() -> new FeeRuleNotFoundException(String.format("Regional base fee rule for this id: ´%s´ does not exist", id)));

        if (city != null) {
            patchedRule.city = city.trim().toLowerCase(Locale.ROOT);
        }
        if (vehicleType != null) {
            patchedRule.vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);
        }
        if (fee != null) {
            patchedRule.fee = fee;
        }

        return baseFeeRuleRepository.save(patchedRule);
    }

    public String deleteRegionalBaseFeeRuleById(Long id) {
        if (baseFeeRuleRepository.existsById(id)) {
            baseFeeRuleRepository.deleteById(id);
            return String.format("Regional base fee rule with id: ´%s´ was deleted", id);
        } else
            throw new FeeRuleNotFoundException(String.format("Regional base fee rule for this id: ´%s´ does not exist", id));
    }

    private void validateRequiredInputs(String city, String vehicleType, Double fee) throws FeeRuleBadRequestException {
        if (city == null) {
            throw new FeeRuleBadRequestException("City must be provided");
        }
        if (vehicleType == null) {
            throw new FeeRuleBadRequestException("Vehicle type must be provided");
        }
        if (fee == null) {
            throw new FeeRuleBadRequestException("Fee must be provided");
        }
    }

    private void validateInputs(String city, String vehicleType, Double fee) throws FeeRuleBadRequestException {

        // todo validate considering cities that are in external weather api
        //  (split by (-), example: Tallinn-Harku)

        city = city.trim().toLowerCase(Locale.ROOT);
        vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);
        //noinspection ConstantConditions
        if (city != null && !city.chars().allMatch(Character::isLetter)) {
            throw new FeeRuleBadRequestException(String.format("City: ´%s´ must contain only letters", city));
        }
        //noinspection ConstantConditions
        if (vehicleType != null && !vehicleType.chars().allMatch(Character::isLetter)) {
            throw new FeeRuleBadRequestException(String.format("Vehicle type: ´%s´ must contain only letters", vehicleType));
        }
        if (fee != null && fee < 0.0) {
            throw new FeeRuleBadRequestException(String.format("Fee: ´%s´ must be positive", vehicleType));
        }
    }

}

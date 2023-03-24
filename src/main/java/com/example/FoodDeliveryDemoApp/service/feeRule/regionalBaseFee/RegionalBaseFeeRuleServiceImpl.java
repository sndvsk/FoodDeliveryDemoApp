package com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee;

import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import com.example.FoodDeliveryDemoApp.repository.rules.RegionalBaseFeeRuleRepository;
import com.example.FoodDeliveryDemoApp.service.externalWeatherData.ExternalWeatherDataService;
import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RegionalBaseFeeRuleServiceImpl implements RegionalBaseFeeRuleService {

    private final RegionalBaseFeeRuleRepository baseFeeRuleRepository;

    private final ExternalWeatherDataService externalWeatherDataService;

    public RegionalBaseFeeRuleServiceImpl(RegionalBaseFeeRuleRepository baseFeeRuleRepository, ExternalWeatherDataService externalWeatherDataService) {
        this.baseFeeRuleRepository = baseFeeRuleRepository;
        this.externalWeatherDataService = externalWeatherDataService;
    }

    // todo add documentation

    private TreeMap<String, Long> getCityNamesAndCodes() throws JAXBException {

        return externalWeatherDataService.getPossibleStationNamesAndCodesFixedNaming();
    }

    private String getKeyForValue(TreeMap<String, Long> map, Long value) {
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        throw new CustomBadRequestException(String.format("Something wrong with the wmo code: ´%s´", value));
    }

    private void validateRequiredInputs(String city, Long wmoCode, String vehicleType, Double fee) throws CustomBadRequestException {

        if (city == null && wmoCode == null) {
            throw new CustomBadRequestException("City or wmo code must be provided");
        }

        if (vehicleType == null) {
            throw new CustomBadRequestException("Vehicle type must be provided");
        }
        if (fee == null) {
            throw new CustomBadRequestException("Fee must be provided");
        }
    }

    private String validateCityAndWmoCode(String city, Long wmoCode, TreeMap<String, Long> cityNamesAndCodes) {

        if (city == null && wmoCode == null) {
            return "";
        }

        if (wmoCode == null) {
            if (!cityNamesAndCodes.containsKey(city.toLowerCase())) {
                throw new CustomBadRequestException(String.format("City: '%s' is not supported by external weather API", city));
            }
            return city;
        } else if (city == null) {
            if (!cityNamesAndCodes.containsValue(wmoCode)) {
                throw new CustomBadRequestException(String.format("Wmo code: '%s' is not supported by external weather API", wmoCode));
            }
            return getKeyForValue(cityNamesAndCodes, wmoCode);
        } else {
            Long expectedWmoCode = cityNamesAndCodes.get(city);

            if (expectedWmoCode == null) {
                if (!cityNamesAndCodes.containsKey(city) && !cityNamesAndCodes.containsValue(wmoCode)) {
                    throw new CustomBadRequestException(String.format("City: ´%s´ and wmo code: '%s' are not supported by external weather API", city, wmoCode));
                }
                if (!cityNamesAndCodes.containsValue(wmoCode)) {
                    throw new CustomBadRequestException(String.format("Wmo code: '%s' is not supported by external weather API", city));
                }
            }

            assert expectedWmoCode != null;
            if (expectedWmoCode.equals(wmoCode)) {
                return expectedWmoCode.toString();
            } else {
                throw new CustomBadRequestException(String.format("Provided wmo code: ´%s´ is not the wmo code for ´%s´", wmoCode, city));
            }
        }
    }

    private void validateInputs(String city, Long wmoCode, String vehicleType, Double fee, TreeMap<String, Long> cityNamesAndCodes) throws CustomBadRequestException {

        if (fee != null && fee < 0.0) {
            throw new CustomBadRequestException(String.format("Fee: ´%s´ must be positive", fee));
        }

        if (vehicleType != null && !vehicleType.chars().allMatch(Character::isLetter)) {
            vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);
            throw new CustomBadRequestException(String.format("Vehicle type: ´%s´ must contain only letters", vehicleType));
        }

        if (city != null) {
            city = city.toLowerCase(Locale.ROOT);
            if (!cityNamesAndCodes.containsKey(city.toLowerCase())) {
                throw new CustomBadRequestException(String.format("City: '%s' is not supported by external weather API", city));
            }
        }

        if (wmoCode != null) {
            // many stations with wmo code 0 so this app does not support them
            if (wmoCode == 0) {
                throw new CustomBadRequestException(String.format("Wmo code: '%s' is not supported by external weather API", city));
            }

            if (!cityNamesAndCodes.containsValue(wmoCode)) {
                throw new CustomBadRequestException(String.format("Wmo code: '%s' is not supported by external weather API", city));
            }
        }

    }

    private void checkExistingVehicleTypesForCity(String city, String vehicleType) {
        TreeMap<String, List<String>> citiesAndVehicles = getAllUniqueCitiesWithVehicleTypes();
        if (citiesAndVehicles.containsKey(city)) {
            List<String> vehicleTypes = citiesAndVehicles.get(city);
            if (vehicleTypes.contains(vehicleType)) {
                throw new CustomBadRequestException(String.format("The city '%s' already has the vehicle type '%s'", city, vehicleType));
            }
        }
    }

    public List<RegionalBaseFeeRule> getAllRegionalBaseFeeRules() {
        List<RegionalBaseFeeRule> ruleList = baseFeeRuleRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new CustomNotFoundException("No regional base fee rules in the database.");
        } else {
            return ruleList;
        }
    }

    public RegionalBaseFeeRule addBaseFeeRule(String city, Long wmoCode, String vehicleType, Double fee) throws JAXBException {

        validateRequiredInputs(city, wmoCode, vehicleType, fee);

        TreeMap<String, Long> cityNamesAndCodes = getCityNamesAndCodes();

        String cityOrWmoCode = validateCityAndWmoCode(city, wmoCode, cityNamesAndCodes);

        if (city == null) {
            city = cityOrWmoCode.toLowerCase(Locale.ROOT);
        }
        if (wmoCode == null) {
            wmoCode = Long.parseLong(cityOrWmoCode);
        }

        vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);

        validateInputs(city, wmoCode, vehicleType, fee, cityNamesAndCodes);
        checkExistingVehicleTypesForCity(city, vehicleType);

        RegionalBaseFeeRule rule = new RegionalBaseFeeRule();

        rule.setCity(city);
        rule.setWmoCode(wmoCode);
        rule.setVehicleType(vehicleType);
        rule.setFee(fee);

        return baseFeeRuleRepository.save(rule);

    }

    public RegionalBaseFeeRule getRegionalBaseFeeRuleById(Long id) {
        Optional<RegionalBaseFeeRule> rule = baseFeeRuleRepository.findById(id);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Regional base fee rule for this id: ´%s´ does not exist", id)));
    }

    public RegionalBaseFeeRule patchRegionalBaseFeeRuleById(Long id, String city, Long wmoCode, String vehicleType, Double fee) throws JAXBException {

        TreeMap<String, Long> cityNamesAndCodes = getCityNamesAndCodes();

        String check = validateCityAndWmoCode(city, wmoCode, cityNamesAndCodes);
        validateInputs(city, wmoCode, vehicleType, fee, cityNamesAndCodes);

        Optional<RegionalBaseFeeRule> rule = baseFeeRuleRepository.findById(id);

        RegionalBaseFeeRule patchedRule = rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Regional base fee rule for this id: ´%s´ does not exist", id)));

        if (city != null) {
            city = city.toLowerCase(Locale.ROOT);
            if (patchedRule.getCity().equalsIgnoreCase(city)) {
                patchedRule.city = city;
            } else {
                checkExistingVehicleTypesForCity(city, vehicleType);
            }
        }
        if (vehicleType != null) {
            vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);
            if (patchedRule.getVehicleType().equalsIgnoreCase(vehicleType)) {
                patchedRule.vehicleType = vehicleType;
            }
            checkExistingVehicleTypesForCity(city, vehicleType);
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
            throw new CustomNotFoundException(String.format("Regional base fee rule for this id: ´%s´ does not exist", id));
    }

    public TreeMap<String, List<String>> getAllUniqueCitiesWithVehicleTypes() {
        List<RegionalBaseFeeRule> rules = baseFeeRuleRepository.findAll();

        TreeMap<String, List<String>> citiesAndVehicleTypes = new TreeMap<>();
        for (RegionalBaseFeeRule rule : rules) {
            String city = rule.getCity();
            String vehicleType = rule.getVehicleType();
            citiesAndVehicleTypes.computeIfAbsent(city, k -> new ArrayList<>()).add(vehicleType);
        }

        return citiesAndVehicleTypes;
    }

    public Set<String> getAllUniqueCities() {
        List<RegionalBaseFeeRule> rules = baseFeeRuleRepository.findAll();
        return rules.stream()
                .map(RegionalBaseFeeRule::getCity)
                .collect(Collectors.toSet());
    }

    public RegionalBaseFeeRule getByCityAndVehicleType(String city, String vehicleType) {
        Optional<RegionalBaseFeeRule> rule = baseFeeRuleRepository.findByCityAndVehicleType(city, vehicleType);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(String.format("Regional base fee rule for this city: ´%s´ and vehicle type: ´%s´ does not exist", city, vehicleType)));
    }

}

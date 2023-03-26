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

    public RegionalBaseFeeRuleServiceImpl(
            RegionalBaseFeeRuleRepository baseFeeRuleRepository,
            ExternalWeatherDataService externalWeatherDataService) {
        this.baseFeeRuleRepository = baseFeeRuleRepository;
        this.externalWeatherDataService = externalWeatherDataService;
    }

    /**
     * Returns a TreeMap of city names and their corresponding wmo codes from the external weather data service
     *
     * @return a TreeMap containing city names and their corresponding wmo codes
     * @throws JAXBException if there is an error while unmarshalling XML from the external service
     */
    private TreeMap<String, Long> getCityNamesAndCodes() throws JAXBException {
        return externalWeatherDataService.getPossibleStationNamesAndCodesFixedNaming();
    }

    /**
     * Returns the key for a given value in a TreeMap.
     *
     * @param map the TreeMap to search
     * @param value the value to search for
     * @return the key corresponding to the given value in the TreeMap
     * @throws CustomBadRequestException if the value is not found in the TreeMap
     */
    private String getKeyForValue(TreeMap<String, Long> map, Long value) {
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        throw new CustomBadRequestException(String.format("Something wrong with the wmo code: ´%s´", value));
    }

    /**
     * Validates the required inputs for a request.
     * Either city or wmo code must be present.
     *
     * @param city the city name to validate
     * @param wmoCode the wmo code to validate
     * @param vehicleType the vehicle type to validate
     * @param fee the fee to validate
     * @throws CustomBadRequestException if any of the required inputs are invalid
     */
    private void validateRequiredInputs(String city, Long wmoCode, String vehicleType, Double fee)
            throws CustomBadRequestException {

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

    /**
     * Validates the city and wmo code for a request. If both city and wmo code are present they must be the same.
     *
     * @param city the city name to validate
     * @param wmoCode the wmo code to validate
     * @param cityNamesAndCodes a TreeMap containing city names and their corresponding wmo codes
     * @return the validated city name or wmo code as a String
     * @throws CustomBadRequestException if either the city or wmo code are invalid
     * @throws CustomBadRequestException if city and wmo code are provided but are not matching
     */
    private String validateCityAndWmoCode(String city, Long wmoCode, TreeMap<String, Long> cityNamesAndCodes)
            throws CustomBadRequestException {

        if (city == null && wmoCode == null) {
            return "";
        }

        if (wmoCode == null) {
            if (!cityNamesAndCodes.containsKey(city.toLowerCase())) {
                throw new CustomBadRequestException(
                        String.format("City: '%s' is not supported by external weather API", city));
            }
            return city;
        } else if (city == null) {
            if (!cityNamesAndCodes.containsValue(wmoCode)) {
                throw new CustomBadRequestException(
                        String.format("Wmo code: '%s' is not supported by external weather API", wmoCode));
            }
            return getKeyForValue(cityNamesAndCodes, wmoCode);
        } else {
            Long expectedWmoCode = cityNamesAndCodes.get(city);

            if (expectedWmoCode == null) {
                if (!cityNamesAndCodes.containsKey(city) && !cityNamesAndCodes.containsValue(wmoCode)) {
                    throw new CustomBadRequestException(
                            String.format("City: ´%s´ and wmo code: '%s' are not supported by external weather API",
                                    city, wmoCode));
                }
                if (!cityNamesAndCodes.containsValue(wmoCode)) {
                    throw new CustomBadRequestException(
                            String.format("Wmo code: '%s' is not supported by external weather API", city));
                }
            }

            assert expectedWmoCode != null;
            if (expectedWmoCode.equals(wmoCode)) {
                return expectedWmoCode.toString();
            } else {
                throw new CustomBadRequestException(
                        String.format("Provided wmo code: ´%s´ is not the wmo code for ´%s´", wmoCode, city));
            }
        }
    }

    /**
     * Validates inputs for creating or patching a RegionalBaseFeeRule.
     *
     * @param city a String representing a city
     * @param wmoCode a Long representing a city code
     * @param vehicleType a String representing a type of vehicle
     * @param fee a Double representing a fee
     * @param cityNamesAndCodes a TreeMap of String and Long, representing a map of city and city codes
     * @throws CustomBadRequestException if the fee is negative
     * @throws CustomBadRequestException if the vehicle type contains non-letter characters
     * @throws CustomBadRequestException if the city is not supported by external weather API
     * @throws CustomBadRequestException if the wmo code is not supported by external weather API
     */
    private void validateInputs(
            String city, Long wmoCode, String vehicleType, Double fee, TreeMap<String, Long> cityNamesAndCodes)
            throws CustomBadRequestException {

        if (fee != null && fee < 0.0) {
            throw new CustomBadRequestException(String.format("Fee: ´%s´ must be positive", fee));
        }

        if (vehicleType != null ) {
            vehicleType = vehicleType
                    .replaceAll(" ", "")
                    .replaceAll("\u00A0", "")
                    .replaceAll("\\u00A0", "");
            if (!vehicleType.chars().allMatch(Character::isLetter)) {
                throw new CustomBadRequestException(
                        String.format("Vehicle type: ´%s´ must contain only letters", vehicleType));
            }
        }

        if (city != null) {
            city = city.toLowerCase(Locale.ROOT);
            if (!cityNamesAndCodes.containsKey(city.toLowerCase())) {
                throw new CustomBadRequestException(
                        String.format("City: '%s' is not supported by external weather API", city));
            }
        }

        if (wmoCode != null) {
            // many stations with wmo code 0 so this app does not support them
            if (wmoCode == 0) {
                throw new CustomBadRequestException(
                        String.format("Wmo code: '%s' is not supported by external weather API", city));
            }

            if (!cityNamesAndCodes.containsValue(wmoCode)) {
                throw new CustomBadRequestException(
                        String.format("Wmo code: '%s' is not supported by external weather API", city));
            }
        }

    }

    /**
     * Checks if a city already has the same type of vehicle.
     *
     * @param city a String representing a city
     * @param vehicleType a String representing a type of vehicle
     * @throws CustomBadRequestException if the city already has the same type of vehicle
     */
    private void checkExistingVehicleTypesForCity(String city, String vehicleType) throws CustomBadRequestException {
        TreeMap<String, List<String>> citiesAndVehicles = getAllUniqueCitiesWithVehicleTypes();
        if (citiesAndVehicles.containsKey(city)) {
            List<String> vehicleTypes = citiesAndVehicles.get(city);
            if (vehicleTypes.contains(vehicleType)) {
                throw new CustomBadRequestException(
                        String.format("The city '%s' already has the vehicle type '%s'", city, vehicleType));
            }
        }
    }

    /**
     * Validates a new city to be added and checks if it already exists.
     *
     * @param newCity a String representing a new city
     * @param oldCity a String representing an existing city
     * @throws CustomBadRequestException if the new city does not exist in the current rules
     */
    private void validateNewCity(String newCity, String oldCity) throws CustomBadRequestException {
        Set<String> check = getAllUniqueCities();
        if (!check.contains(newCity.toLowerCase(Locale.ROOT))) {
            throw new CustomBadRequestException(
                    String.format("You dont have rules for: ´%s´. Consider creating new rule instead of patching ´%s´",
                            newCity, oldCity));
        }
    }

    /**
     * Returns a List of all RegionalBaseFeeRules
     *
     * @return a List of RegionalBaseFeeRule objects
     * @throws CustomNotFoundException if no rules exist in the database
     */
    public List<RegionalBaseFeeRule> getAllRegionalBaseFeeRules() throws CustomNotFoundException {
        List<RegionalBaseFeeRule> ruleList = baseFeeRuleRepository.findAll();
        if (ruleList.isEmpty()) {
            throw new CustomNotFoundException("No regional base fee rules in the database.");
        } else {
            return ruleList;
        }
    }

    /**
     * Creates a new RegionalBaseFeeRule. Goes through all needed validations.
     *
     * @param city a String representing a city
     * @param wmoCode a Long representing a city code
     * @param vehicleType a String representing a type of vehicle
     * @param fee a Double representing a fee
     * @return a RegionalBaseFeeRule object
     * @throws JAXBException if there is an error parsing the XML request body
     */
    public RegionalBaseFeeRule addBaseFeeRule(String city, Long wmoCode, String vehicleType, Double fee)
            throws JAXBException, CustomBadRequestException {

        validateRequiredInputs(city, wmoCode, vehicleType, fee);

        TreeMap<String, Long> cityNamesAndCodes = getCityNamesAndCodes();

        String cityOrWmoCode = validateCityAndWmoCode(city, wmoCode, cityNamesAndCodes);

        if (city == null) {
            city = cityOrWmoCode.toLowerCase(Locale.ROOT);
        }
        if (wmoCode == null) {
            if (cityOrWmoCode.equals(city)) {
                wmoCode = cityNamesAndCodes.get(city);
            } else {
                wmoCode = Long.parseLong(cityOrWmoCode);
            }
        }

        vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);

        validateInputs(city, wmoCode, vehicleType, fee, cityNamesAndCodes);
        city = city.toLowerCase(Locale.ROOT);
        checkExistingVehicleTypesForCity(city, vehicleType);

        RegionalBaseFeeRule rule = new RegionalBaseFeeRule();

        rule.setCity(city);
        rule.setWmoCode(wmoCode);
        rule.setVehicleType(vehicleType);
        rule.setFee(fee);

        return baseFeeRuleRepository.save(rule);

    }

    /**
     * Returns a RegionalBaseFeeRule by id.
     *
     * @param id a Long representing an id
     * @return a RegionalBaseFeeRule object
     * @throws CustomNotFoundException if no rule exists with the provided id
     */
    public RegionalBaseFeeRule getRegionalBaseFeeRuleById(Long id) {
        Optional<RegionalBaseFeeRule> rule = baseFeeRuleRepository.findById(id);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(
                        String.format("Regional base fee rule for this id: ´%s´ does not exist", id)));
    }

    /**
     * Updates a RegionalBaseFeeRule by id.
     *
     * @param id a Long representing an id
     * @param city a String representing a city
     * @param wmoCode a Long representing a city code
     * @param vehicleType a String representing a type of vehicle
     * @param fee a Double representing a fee
     * @return a RegionalBaseFeeRule object
     * @throws JAXBException if there is an error parsing the XML request body
     * @throws CustomNotFoundException if no rule exists with the provided id
     */
    public RegionalBaseFeeRule patchRegionalBaseFeeRuleById(
            Long id, String city, Long wmoCode, String vehicleType, Double fee)
            throws JAXBException, CustomNotFoundException {
        TreeMap<String, Long> cityNamesAndCodes = getCityNamesAndCodes();

        validateCityAndWmoCode(city, wmoCode, cityNamesAndCodes);
        validateInputs(city, wmoCode, vehicleType, fee, cityNamesAndCodes);

        Optional<RegionalBaseFeeRule> rule = baseFeeRuleRepository.findById(id);

        RegionalBaseFeeRule patchedRule = rule
                .orElseThrow(() -> new CustomNotFoundException(
                        String.format("Regional base fee rule for this id: ´%s´ does not exist", id)));

        if (city != null) {
            city = city.toLowerCase(Locale.ROOT);
            if (patchedRule.getCity().equalsIgnoreCase(city)) {
                checkExistingVehicleTypesForCity(city, vehicleType);
            } else {
                validateNewCity(city, patchedRule.getCity());
                //getByCityAndVehicleType(patchedRule.getCity(), vehicleType);
            }
            patchedRule.city = city;
        }
        if (vehicleType != null) {
            vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);
            if (patchedRule.getVehicleType().equalsIgnoreCase(vehicleType)) {
                checkExistingVehicleTypesForCity(city, vehicleType);
                patchedRule.vehicleType = vehicleType;
            }
            checkExistingVehicleTypesForCity(city, vehicleType);
        }
        if (fee != null) {
            patchedRule.fee = fee;
        }

        return baseFeeRuleRepository.save(patchedRule);
    }

    /**
     * Deletes a regional base fee rule by its id
     *
     * @param id the id of the regional base fee rule to be deleted
     * @return a message indicating whether the rule was successfully deleted or if it did not exist
     * @throws CustomNotFoundException if the rule with the given id does not exist
     */
    public String deleteRegionalBaseFeeRuleById(Long id) throws CustomNotFoundException {
        if (baseFeeRuleRepository.existsById(id)) {
            baseFeeRuleRepository.deleteById(id);
            return String.format("Regional base fee rule with id: ´%s´ was deleted", id);
        } else
            throw new CustomNotFoundException(
                    String.format("Regional base fee rule for this id: ´%s´ does not exist", id));
    }

    /**
     * Retrieves a TreeMap of unique cities with their corresponding vehicle types based on all regional base fee rules.
     *
     * @return a TreeMap containing unique cities as keys and lists of vehicle types as values
     */
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

    /**
     * Retrieves a Set of unique cities based on all regional base fee rules.
     *
     * @return a Set of unique cities
     */
    public Set<String> getAllUniqueCities() {
        List<RegionalBaseFeeRule> rules = baseFeeRuleRepository.findAll();
        return rules.stream()
                .map(RegionalBaseFeeRule::getCity)
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves the regional base fee rule for a specific city and vehicle type.
     * @param city the city of the regional base fee rule to be retrieved
     * @param vehicleType the vehicle type of the regional base fee rule to be retrieved
     * @return the regional base fee rule that matches the given city and vehicle type
     * @throws CustomNotFoundException if the rule for the given city and vehicle type does not exist
     */
    public RegionalBaseFeeRule getByCityAndVehicleType(String city, String vehicleType) throws CustomNotFoundException {
        Optional<RegionalBaseFeeRule> rule = baseFeeRuleRepository.findByCityAndVehicleType(city, vehicleType);
        return rule
                .orElseThrow(() -> new CustomNotFoundException(
                        String.format(
                                "Regional base fee rule for this city: ´%s´ and vehicle type: ´%s´ does not exist",
                                city, vehicleType)));
    }

}

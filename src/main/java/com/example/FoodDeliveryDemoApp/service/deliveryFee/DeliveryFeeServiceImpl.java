package com.example.FoodDeliveryDemoApp.service.deliveryFee;

import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.model.DeliveryFee;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.repository.DeliveryFeeRepository;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.airTemperatureRule.ExtraFeeAirTemperatureRuleService;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.weatherPhenomenonRule.ExtraFeeWeatherPhenomenonRuleService;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.windSpeedRule.ExtraFeeWindSpeedRuleService;
import com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee.RegionalBaseFeeRuleService;
import com.example.FoodDeliveryDemoApp.service.weatherData.WeatherDataService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;


@Component
public class DeliveryFeeServiceImpl implements DeliveryFeeService {

    private final DeliveryFeeRepository deliveryFeeRepository;

    private final WeatherDataService weatherDataService;

    private final ExtraFeeAirTemperatureRuleService airTemperatureRuleService;
    private final ExtraFeeWindSpeedRuleService windSpeedRuleService;
    private final ExtraFeeWeatherPhenomenonRuleService weatherPhenomenonRuleService;
    private final RegionalBaseFeeRuleService baseFeeRuleService;

    public DeliveryFeeServiceImpl(DeliveryFeeRepository deliveryFeeRepository,
                                  WeatherDataService weatherDataService,
                                  ExtraFeeAirTemperatureRuleService airTemperatureRuleService,
                                  ExtraFeeWindSpeedRuleService windSpeedRuleService,
                                  ExtraFeeWeatherPhenomenonRuleService weatherPhenomenonRuleService,
                                  RegionalBaseFeeRuleService baseFeeRuleService) {
        this.deliveryFeeRepository = deliveryFeeRepository;
        this.weatherDataService = weatherDataService;
        this.airTemperatureRuleService = airTemperatureRuleService;
        this.windSpeedRuleService = windSpeedRuleService;
        this.weatherPhenomenonRuleService = weatherPhenomenonRuleService;
        this.baseFeeRuleService = baseFeeRuleService;
    }

    private TreeMap<String, List<String>> getExistingVehicleTypesForCities() {
        return baseFeeRuleService.getAllUniqueCitiesWithVehicleTypes();
    }

    private void checkExistingVehicleTypesForCity(String city, String vehicleType) throws CustomBadRequestException {
        TreeMap<String, List<String>> citiesAndVehicles = getExistingVehicleTypesForCities();
        if (citiesAndVehicles.containsKey(city)) {
            List<String> vehicleTypes = citiesAndVehicles.get(city);
            if (!vehicleTypes.contains(vehicleType)) {
                throw new CustomBadRequestException(String.format("This city: ´%s´ does not currently support this vehicle type: ´%s´", city, vehicleType));
            }
        } else {
            throw new CustomBadRequestException(String.format("City: ´%s´ argument is invalid or not supported.", city));
        }
    }

    /**
     * Validates the inputs provided for calculating the delivery fee.
     *
     * @param city the city for which to calculate the delivery fee. Cannot be null or empty. Must be one of "tallinn", "tartu", or "pärnu".
     * @param vehicleType the type of vehicle used for delivery Cannot be null or empty. Must be one of "car", "scooter", or "bike".
     * @throws CustomBadRequestException if there is an exception validating the inputs provided. Will contain a single DeliveryFeeException object.
     */
    private void validateRequiredInputs(String city, String vehicleType) throws CustomBadRequestException {
        //List<CustomBadRequestException> exceptionList = new ArrayList<>();

        if (city == null || city.isEmpty()) {
            throw new CustomBadRequestException(String.format("Parameter city: ´%s´ is empty", city));
        }
        if (vehicleType == null || vehicleType.isEmpty()) {
            throw new CustomBadRequestException(String.format("Parameter vehicle type: ´%s´ is empty", vehicleType));
        }
        city = city.toLowerCase(Locale.ROOT);
        vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);

        checkExistingVehicleTypesForCity(city, vehicleType);
    }

    /**
     * Creates a new DeliveryFeeException with the given message.
     *
     * @param message the error message to include in the exception
     * @return the newly created DeliveryFeeException
     */
    private CustomBadRequestException createException(String message) {
        return new CustomBadRequestException(message);
    }

    /**
     * Retrieves a list of all DeliveryFee objects from the deliveryFeeRepository.
     *
     * @return a list of all DeliveryFee objects in the deliveryFeeRepository
     */
    public List<DeliveryFee> getAllDeliveryFees() throws CustomNotFoundException {
        List<DeliveryFee> deliveryFeeList = deliveryFeeRepository.findAll();
        if (deliveryFeeList.isEmpty()) {
            throw new CustomNotFoundException("There are no calculated delivery fees");
        } else {
            return deliveryFeeList;
        }
    }

    /**
     * Retrieves a DeliveryFee object from the deliveryFeeRepository by its ID.
     *
     * @param id the ID of the delivery fee to retrieve
     * @return the DeliveryFee object with the specified ID
     * @throws CustomNotFoundException if no DeliveryFee object with the specified ID exists
     */
    public DeliveryFee getDeliveryFeeById(Long id) throws CustomNotFoundException {
        Optional<DeliveryFee> deliveryFee = deliveryFeeRepository.findById(id);

        return deliveryFee.
                orElseThrow(() -> new CustomNotFoundException("This delivery fee calculation does not exist"));

    }

    /**
     * Overload of the {@link #calculateAndSaveDeliveryFee(String, String, OffsetDateTime)}method with a default {@code dateTime}.
     */
    public DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType) throws CustomBadRequestException {
        return calculateAndSaveDeliveryFee(city, vehicleType, null);
    }

    /**
     * Main method to validate inputs, calculate delivery fee, create DeliveryFee object and save it to database.
     * Calculates the delivery fee and returns an DeliveryFee object with the result.
     *
     * @param city the city for which to calculate the delivery fee
     * @param vehicleType the type of vehicle used for delivery
     * @param dateTime the datetime of when the delivery takes place
     * @return a DeliveryFee object with the delivery fee calculation result
     * @throws CustomBadRequestException if there is an error in the input validation or the fee calculation
     */
    public DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime) throws CustomBadRequestException {
        validateRequiredInputs(city, vehicleType);

        city = city.trim().toLowerCase(Locale.ROOT);
        vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);

        double deliveryFee = calculateDeliveryFee(city, vehicleType, dateTime);
        DeliveryFee returnDeliveryFee = createNewDeliveryFee(city, vehicleType, deliveryFee, dateTime);

        saveDeliveryFee(returnDeliveryFee);

        return returnDeliveryFee;

    }

    /**
     * Calculates the delivery fee for a given city and vehicle type at a specific date and time.
     * It takes into account the regional base fee and the weather condition fee.
     *
     * @param city the city for which to calculate the delivery fee
     * @param vehicleType the type of vehicle used for delivery
     * @param dateTime the date and time for which to calculate the delivery fee. If null, the current date and time will be used (optional)
     * @return the delivery fee for the given city, vehicle type, and datetime if provided
     */
    public Double calculateDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime) {

        double regionalFee = calculateRegionalBaseFee(city, vehicleType);
        double weatherConditionFee = calculateWeatherConditionFee(city, vehicleType, dateTime);

        return regionalFee + weatherConditionFee;
    }

    /**
     * Calculates the weather condition fee for a given city and vehicle type. It takes into account the air temperature, wind speed and weather phenomenon.
     *
     * @param city the city for which to calculate the delivery fee
     * @param vehicleType the type of vehicle used for delivery
     * @return the weather condition fee for the city and vehicle type
     */
    public Double calculateRegionalBaseFee(String city, String vehicleType) {
        RegionalBaseFeeRule rule = baseFeeRuleService.getByCityAndVehicleType(city, vehicleType);
        @SuppressWarnings("UnnecessaryLocalVariable")
        Double fee = rule.getFee();
        return fee;
    }

    /**
     * Calculates the weather condition fee for a given city and vehicle type at a specific datetime. It takes into account the air temperature, wind speed and weather phenomenon.
     *
     * @param city the city for which to calculate the delivery fee
     * @param vehicleType the type of vehicle used for delivery
     * @param dateTime the date and time for which to calculate the delivery fee. If null, the current date and time will be used (optional)
     * @return the weather condition fee for the specified city, vehicle type, and datetime
     */
    public Double calculateWeatherConditionFee(String city, String vehicleType, OffsetDateTime dateTime) {

        WeatherData weatherData = weatherDataService.getLastDataByCity(city, dateTime);

        // A map that maps each vehicle type to its corresponding fee calculation function
        Map<String, Function<WeatherData, Double>> vehicleTypeToFeeFunction = Map.of(
                "car", weatherData1 -> 0.0,
                "scooter", weatherData1 -> calculateAirTemperatureFee(weatherData1.getAirTemperature())
                        + calculateWeatherPhenomenonFee(weatherData1.getWeatherPhenomenon()),
                "bike", weatherData1 -> calculateAirTemperatureFee(weatherData1.getAirTemperature())
                        + calculateWindSpeedFee(weatherData1.getWindSpeed())
                        + calculateWeatherPhenomenonFee(weatherData1.getWeatherPhenomenon())
        );

        Function<WeatherData, Double> feeFunction = vehicleTypeToFeeFunction.get(vehicleType);

        return feeFunction.apply(weatherData);
    }

    /**
     * Calculates the fee based on the air temperature.
     *
     * @param airTemperature the air temperature
     * @return the fee based on the air temperature
     */
    public double calculateAirTemperatureFee(double airTemperature) throws CustomBadRequestException {
        ExtraFeeAirTemperatureRule rule = airTemperatureRuleService.getByTemperature(airTemperature);
        Double fee = rule.getFee();
        if (fee < 0) {
            throw new CustomBadRequestException(String.format("Usage of selected vehicle type is forbidden: air temperature ´%s´ is too low", airTemperature));
        }
        return fee;
    }

    /**
     * Calculates the fee based on the wind speed. (only for bike)
     *
     * @param windSpeed the wind speed
     * @return the fee based on the wind speed
     * @throws CustomBadRequestException if the wind speed is greater than 20.0
     */
    public double calculateWindSpeedFee(Double windSpeed) throws CustomBadRequestException {
        ExtraFeeWindSpeedRule rule = windSpeedRuleService.getByWindSpeed(windSpeed);
        Double fee = rule.getFee();
        if (fee < 0) {
            throw new CustomBadRequestException(String.format("Usage of selected vehicle type is forbidden: wind speed ´%s´ is too high", windSpeed));
        }
        return fee;
    }

    /**
     * Calculates the fee based on the weather phenomenon. (only for scooter and bike)
     *
     * @param weatherPhenomenon the name of weather phenomenon
     * @return the fee based on the weather phenomenon
     * @throws CustomBadRequestException if usage of selected vehicle type is forbidden in those weather conditions
     */
    public Double calculateWeatherPhenomenonFee(String weatherPhenomenon) throws CustomBadRequestException {
        ExtraFeeWeatherPhenomenonRule rule = weatherPhenomenonRuleService.getByWeatherPhenomenonName(weatherPhenomenon);
        Double fee = rule.getFee();
        if (fee < 0) {
            throw new CustomBadRequestException(String.format("Usage of selected vehicle type is forbidden: weather phenomenon ´%s´ is dangerous", weatherPhenomenon));
        }
        return fee;
    }

    /**
     * Creates a new DeliveryFee object with the specified parameters.
     *
     * @param city the city for which to calculate the delivery fee
     * @param vehicleType the type of vehicle used for delivery
     * @param deliveryFeePrice the calculated delivery fee
     * @param dateTime the date and time for which to calculate the delivery fee. If null, the current date and time will be used (optional)
     * @return a new DeliveryFee object with the specified parameters
     */
    public DeliveryFee createNewDeliveryFee(String city, String vehicleType, double deliveryFeePrice, OffsetDateTime dateTime) {

        DeliveryFee deliveryFee = new DeliveryFee();
        deliveryFee.setCity(city);
        deliveryFee.setVehicleType(vehicleType);
        deliveryFee.setDeliveryFee(deliveryFeePrice);

        WeatherData weatherData = weatherDataService.getLastDataByCity(city, dateTime);

        if (dateTime == null) {
            deliveryFee.setTimestamp(Instant.now());
        } else {
            deliveryFee.setTimestamp(weatherData.getTimestamp());
        }

        deliveryFee.setWeatherId(weatherData.getId());

        return deliveryFee;
    }

    /**
     * Saves the order data to the repository.
     *
     * @param deliveryFee the delivery fee object to be saved
     */
    public void saveDeliveryFee(DeliveryFee deliveryFee) {
        deliveryFeeRepository.save(deliveryFee);
    }

}

package com.example.FoodDeliveryDemoApp.service.deliveryFee;

import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomExceptionList;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.model.DeliveryFee;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.model.rules.RegionalBaseFeeRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeAirTemperatureRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWindSpeedRule;
import com.example.FoodDeliveryDemoApp.repository.DeliveryFeeRepository;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.airTemperatureRule.ExtraFeeAirTemperatureRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.weatherPhenomenonRule.ExtraFeeWeatherPhenomenonRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.windSpeedRule.ExtraFeeWindSpeedRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee.RegionalBaseFeeRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.weatherData.WeatherDataServiceImpl;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;


@Component
public class DeliveryFeeServiceImpl implements DeliveryFeeService {

    private final DeliveryFeeRepository deliveryFeeRepository;

    private final WeatherDataServiceImpl weatherDataService;

    private final ExtraFeeAirTemperatureRuleServiceImpl airTemperatureRuleService;
    private final ExtraFeeWindSpeedRuleServiceImpl windSpeedRuleService;
    private final ExtraFeeWeatherPhenomenonRuleServiceImpl weatherPhenomenonRuleService;
    private final RegionalBaseFeeRuleServiceImpl baseFeeRuleService;

    public DeliveryFeeServiceImpl(DeliveryFeeRepository deliveryFeeRepository,
                                  WeatherDataServiceImpl weatherDataService, ExtraFeeAirTemperatureRuleServiceImpl airTemperatureRuleService, ExtraFeeWindSpeedRuleServiceImpl windSpeedRuleService, ExtraFeeWeatherPhenomenonRuleServiceImpl weatherPhenomenonRuleService, RegionalBaseFeeRuleServiceImpl baseFeeRuleService) {
        this.deliveryFeeRepository = deliveryFeeRepository;
        this.weatherDataService = weatherDataService;
        this.airTemperatureRuleService = airTemperatureRuleService;
        this.windSpeedRuleService = windSpeedRuleService;
        this.weatherPhenomenonRuleService = weatherPhenomenonRuleService;
        this.baseFeeRuleService = baseFeeRuleService;
    }

    /**
     * Validates the inputs provided for calculating the delivery fee.
     *
     * @param city the city for which to calculate the delivery fee. Cannot be null or empty. Must be one of "tallinn", "tartu", or "pärnu".
     * @param vehicleType the type of vehicle used for delivery Cannot be null or empty. Must be one of "car", "scooter", or "bike".
     * @throws CustomBadRequestException if there is an exception validating the inputs provided. Will contain a single DeliveryFeeException object.
     * @throws CustomExceptionList if there are multiple exceptions validating the inputs provided. Will contain a list of DeliveryFeeException objects.
     */
    private void validateRequiredInputs(String city, String vehicleType) throws CustomBadRequestException, CustomExceptionList {
        Set<String> validCityNames = new HashSet<>(
                Arrays.asList(
                        "tallinn",
                        "tartu",
                        "pärnu")
        );
        Set<String> validVehicleTypes = new HashSet<>(
                Arrays.asList(
                        "car",
                        "scooter",
                        "bike")
        );

        List<CustomBadRequestException> exceptionList = new ArrayList<>();

        if (city == null || city.isEmpty()) {
            exceptionList.add(createException("Parameter city is empty."));
        } else if (!validCityNames.contains(city)) {
            exceptionList.add(createException(
                    String.format("City: ´%s´ argument is invalid or not supported.", city)));
        }

        if (vehicleType == null || vehicleType.isEmpty()) {
            exceptionList.add(createException("Parameter vehicle type is empty."));
        } else if (!validVehicleTypes.contains(vehicleType)) {
            exceptionList.add(createException(
                    String.format("Vehicle type: ´%s´ argument is invalid or not supported.", vehicleType)));
        }


        if (!exceptionList.isEmpty()) {
            throw exceptionList.size() == 1 ? new CustomBadRequestException(exceptionList) : new CustomExceptionList(exceptionList);
        }

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
    public DeliveryFee getDeliveryFeeById(Long id) throws CustomBadRequestException {
        Optional<DeliveryFee> deliveryFee = deliveryFeeRepository.findById(id);

        return deliveryFee.
                orElseThrow(() -> new CustomNotFoundException("This delivery fee calculation does not exist"));

    }

    /**
     * Overload of the {@link #calculateAndSaveDeliveryFee(String, String, OffsetDateTime)}method with a default {@code dateTime}.
     */
    public DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType) throws CustomBadRequestException, CustomExceptionList {
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
     * @throws CustomExceptionList if there are multiple errors in the input validation
     */
    public DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime) throws CustomBadRequestException, CustomExceptionList {

        city = city.trim().toLowerCase(Locale.ROOT);
        vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);

        validateRequiredInputs(city, vehicleType);

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
    public double calculateAirTemperatureFee(double airTemperature) {
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
    public double calculateWindSpeedFee(Double windSpeed) {
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
    public Double calculateWeatherPhenomenonFee(String weatherPhenomenon) {
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

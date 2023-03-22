package com.example.FoodDeliveryDemoApp.service.DeliveryFee;

import com.example.FoodDeliveryDemoApp.exception.deliveryfee.DeliveryFeeException;
import com.example.FoodDeliveryDemoApp.exception.deliveryfee.DeliveryFeeExceptionsList;
import com.example.FoodDeliveryDemoApp.exception.deliveryfee.DeliveryFeeNotFoundException;
import com.example.FoodDeliveryDemoApp.model.DeliveryFee;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.repository.DeliveryFeeRepository;
import com.example.FoodDeliveryDemoApp.service.WeatherData.WeatherDataServiceImpl;
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

    public DeliveryFeeServiceImpl(DeliveryFeeRepository deliveryFeeRepository, WeatherDataServiceImpl weatherDataService) {
        this.deliveryFeeRepository = deliveryFeeRepository;
        this.weatherDataService = weatherDataService;
    }

    // ´static final´ to avoid recreating every time the method ´calculateRegionalBaseFee´ is called
    private static final Map<String, Map<String, Double>> cityVehicleFees = Map.of(
            "tallinn",
            Map.of("car", 4.0, "scooter", 3.5, "bike", 3.0),
            "tartu",
            Map.of("car", 3.5, "scooter", 3.0, "bike", 2.5),
            "pärnu",
            Map.of("car", 3.0, "scooter", 2.5, "bike", 2.0)
    );

    // ´static final´ to avoid recreating every time the method ´calculateWeatherPhenomenonFee´ is called
    private static final Map<String, Double> weatherPhenomenonFees = Map.ofEntries(
            Map.entry("Light sleet", 1.0),
            Map.entry("Moderate sleet", 1.0),
            Map.entry("Light snow shower", 1.0),
            Map.entry("Moderate snow shower", 1.0),
            Map.entry("Heavy snow shower", 1.0),
            Map.entry("Light snowfall", 1.0),
            Map.entry("Moderate snowfall", 1.0),
            Map.entry("Heavy snowfall", 1.0),
            Map.entry("Blowing snow", 1.0),
            Map.entry("Drifting snow", 1.0),
            Map.entry("Light shower", 0.5),
            Map.entry("Moderate shower", 0.5),
            Map.entry("Heavy shower", 0.5),
            Map.entry("Light rain", 0.5),
            Map.entry("Moderate rain", 0.5),
            Map.entry("Heavy rain", 0.5),
            Map.entry("Glaze", -1.0),
            Map.entry("Hail", -1.0),
            Map.entry("Thunder", -1.0),
            Map.entry("Thunderstorm", -1.0)
    );

    /**
     * Saves the order data to the repository.
     *
     * @param deliveryFee the delivery fee object to be saved
     */
    public void saveDeliveryFee(DeliveryFee deliveryFee) {
        deliveryFeeRepository.save(deliveryFee);
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
        Map<String, Double> totalFees = cityVehicleFees.get(city);
        return totalFees.get(vehicleType);
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
        if (airTemperature < -10.0) {
            return 1;
        } else if (airTemperature < 0.0) {
            return 0.5;
        }
        return 0;
    }

    /**
     * Calculates the fee based on the wind speed. (only for bike)
     *
     * @param windSpeed the wind speed
     * @return the fee based on the wind speed
     * @throws DeliveryFeeException if the wind speed is greater than 20.0
     */
    public double calculateWindSpeedFee(Double windSpeed) {
        if (windSpeed > 20.0) {
            throw new DeliveryFeeException("Usage of selected vehicle type is forbidden: wind speed too high");
        } else if (windSpeed > 10.0) {
            return 0.5;
        }
        return 0;
    }

    /**
     * Calculates the fee based on the weather phenomenon. (only for scooter and bike)
     *
     * @param weatherPhenomenon the name of weather phenomenon
     * @return the fee based on the weather phenomenon
     * @throws DeliveryFeeException if usage of selected vehicle type is forbidden in those weather conditions
     */
    public Double calculateWeatherPhenomenonFee(String weatherPhenomenon) {
        Double fee = weatherPhenomenonFees.getOrDefault(weatherPhenomenon, 0.0);
        if (fee == -1.0) {
            throw new DeliveryFeeException("Usage of selected vehicle type is forbidden");
        }
        return fee;
    }

    /**
     * Validates the inputs provided for calculating the delivery fee.
     *
     * @param city the city for which to calculate the delivery fee. Cannot be null or empty. Must be one of "tallinn", "tartu", or "pärnu".
     * @param vehicleType the type of vehicle used for delivery Cannot be null or empty. Must be one of "car", "scooter", or "bike".
     * @throws DeliveryFeeException if there is an exception validating the inputs provided. Will contain a single DeliveryFeeException object.
     * @throws DeliveryFeeExceptionsList if there are multiple exceptions validating the inputs provided. Will contain a list of DeliveryFeeException objects.
     */
    public void validateInputs(String city, String vehicleType) throws DeliveryFeeException, DeliveryFeeExceptionsList {
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

        List<DeliveryFeeException> exceptionList = new ArrayList<>();

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
            throw exceptionList.size() == 1 ? new DeliveryFeeException(exceptionList) : new DeliveryFeeExceptionsList(exceptionList);
        }

    }

    /**
     * Creates a new DeliveryFeeException with the given message.
     *
     * @param message the error message to include in the exception
     * @return the newly created DeliveryFeeException
     */
    public DeliveryFeeException createException(String message) {
        return new DeliveryFeeException(message);
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
            deliveryFee.setTimestamp(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        } else {
            deliveryFee.setTimestamp(dateTime.truncatedTo(ChronoUnit.SECONDS).toInstant());
        }

        deliveryFee.setWeatherId(weatherData.getId());

        return deliveryFee;
    }

    /**
     * Overload of the {@link #calculateAndSaveDeliveryFee(String, String, OffsetDateTime)}method with a default {@code dateTime}.
     */
    public DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType) throws DeliveryFeeException, DeliveryFeeExceptionsList {
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
     * @throws DeliveryFeeException if there is an error in the input validation or the fee calculation
     * @throws DeliveryFeeExceptionsList if there are multiple errors in the input validation
     */
    public DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime) throws DeliveryFeeException, DeliveryFeeExceptionsList {

        city = city.trim().toLowerCase(Locale.ROOT);
        vehicleType = vehicleType.trim().toLowerCase(Locale.ROOT);

        validateInputs(city, vehicleType);

        double deliveryFee = calculateDeliveryFee(city, vehicleType, dateTime);
        DeliveryFee returnDeliveryFee = createNewDeliveryFee(city, vehicleType, deliveryFee, dateTime);

        saveDeliveryFee(returnDeliveryFee);

        return returnDeliveryFee;

    }

    /**
     * Retrieves a DeliveryFee object from the deliveryFeeRepository by its ID.
     *
     * @param id the ID of the delivery fee to retrieve
     * @return the DeliveryFee object with the specified ID
     * @throws DeliveryFeeNotFoundException if no DeliveryFee object with the specified ID exists
     */
    public DeliveryFee getDeliveryFeeById(Long id) throws DeliveryFeeException {
        Optional<DeliveryFee> deliveryFee = deliveryFeeRepository.findById(id);

        return deliveryFee.
                orElseThrow(() -> new DeliveryFeeNotFoundException("This delivery fee calculation does not exist"));

    }

    /**
     * Retrieves a list of all DeliveryFee objects from the deliveryFeeRepository.
     *
     * @return a list of all DeliveryFee objects in the deliveryFeeRepository
     */
    public List<DeliveryFee> getAllDeliveryFees() throws DeliveryFeeNotFoundException {
        List<DeliveryFee> deliveryFeeList = deliveryFeeRepository.findAll();
        if (deliveryFeeList.isEmpty()) {
            throw new DeliveryFeeNotFoundException("There are no calculated delivery fees");
        } else {
            return deliveryFeeList;
        }
    }

}

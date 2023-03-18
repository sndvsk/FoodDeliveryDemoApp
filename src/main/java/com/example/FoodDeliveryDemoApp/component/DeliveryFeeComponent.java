package com.example.FoodDeliveryDemoApp.component;

import com.example.FoodDeliveryDemoApp.exception.DeliveryFeeException;
import com.example.FoodDeliveryDemoApp.exception.DeliveryFeeExceptionsList;
import com.example.FoodDeliveryDemoApp.model.OrderData;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.repository.OrderDataRepository;
import com.example.FoodDeliveryDemoApp.repository.WeatherDataRepository;
import com.example.FoodDeliveryDemoApp.util.Utils;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Component
public class DeliveryFeeComponent {

    private final OrderDataRepository orderDataRepository;

    private final WeatherDataComponent weatherDataComponent;

    public DeliveryFeeComponent(OrderDataRepository orderDataRepository, WeatherDataComponent weatherDataComponent) {
        this.orderDataRepository = orderDataRepository;
        this.weatherDataComponent = weatherDataComponent;
    }

    public void saveOrderData(OrderData orderData) {
        orderDataRepository.save(orderData);
    }

    public double calculateDeliveryFee(String city, String vehicleType) {

        double regionalFee = calculateRegionalBaseFee(city, vehicleType);
        double weatherConditionFee = calculateWeatherConditionFee(city, vehicleType);

        return regionalFee + weatherConditionFee;
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

    public Double calculateRegionalBaseFee(String city, String vehicleType) {

        Map<String, Double> totalFees = cityVehicleFees.get(city);
        if (totalFees != null) {
            return totalFees.get(vehicleType);
        }
        return null;
    }

    public double calculateWeatherConditionFee(String city, String vehicleType) {
        WeatherData weatherData = getLastDataByCityFromWeatherComponent(city);

        Map<String, Function<WeatherData, Double>> vehicleTypeToFeeFunction = Map.of(
                "car", weatherData1 -> 0.0,
                "scooter", weatherData1 -> calculateAirTemperatureFee(weatherData1.getAirTemperature())
                        + calculateWeatherPhenomenonFee(weatherData1.getWeatherPhenomenon()),
                "bike", weatherData1 -> calculateAirTemperatureFee(weatherData1.getAirTemperature())
                        + calculateWindSpeedFee(weatherData1.getWindSpeed())
                        + calculateWeatherPhenomenonFee(weatherData1.getWeatherPhenomenon())
        );

        Function<WeatherData, Double> feeFunction = vehicleTypeToFeeFunction.get(vehicleType);
        if (feeFunction == null) {
            return 0.0;
        }

        return feeFunction.apply(weatherData);
    }


    private double calculateAirTemperatureFee(double airTemperature) {
        if (airTemperature < -10.0) {
            return 1;
        }
        if (airTemperature > -10.0 && airTemperature < 0.0) {
            return 0.5;
        }
        return 0;
    }

    private double calculateWindSpeedFee(Double windSpeed) {
        final double maxWindSpeed = 20.0;
        if (windSpeed > maxWindSpeed) {
            throw new DeliveryFeeException("Usage of selected vehicle type is forbidden: wind speed too high");
        }
        if (windSpeed > 10 && windSpeed <= maxWindSpeed) {
            return 0.5;
        }
        return 0;
    }

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

    private double calculateWeatherPhenomenonFee(String weatherPhenomenon) {
        Double fee = weatherPhenomenonFees.getOrDefault(weatherPhenomenon, 0.0);
        if (fee == -1.0) {
            throw new DeliveryFeeException("Usage of selected vehicle type is forbidden");
        }
        return fee;
    }

    public List<DeliveryFeeException> validateInputs(String city, String vehicleType) {
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

        if (city == null || city.trim().isEmpty()) {
            exceptionList.add(
                    createException(
                            String.format("City: ´%s´ is empty.", Utils.capitalizeFirstLetter(city))));
        } else if (!validCityNames.contains(city.trim().toLowerCase())) {
            exceptionList.add(
                    createException(
                            String.format("City: ´%s´ argument is invalid or not supported.", Utils.capitalizeFirstLetter(city))));
        }

        if (vehicleType == null || vehicleType.trim().isEmpty()) {
            exceptionList.add(
                    createException(
                            String.format("Vehicle type: ´%s´ is empty.", Utils.capitalizeFirstLetter(vehicleType))));
        } else if (!validVehicleTypes.contains(vehicleType.trim().toLowerCase())) {
            exceptionList.add(
                    createException(
                            String.format("Vehicle type: ´%s´ is invalid or not supported.", Utils.capitalizeFirstLetter(vehicleType))));
        }

        return exceptionList;
    }

    private DeliveryFeeException createException(String message) {
        return new DeliveryFeeException(message);
    }

    public OrderData createNewOrderData(String city, String vehicleType, double deliveryFee) {

        OrderData orderData = new OrderData();
        orderData.setCity(Utils.capitalizeFirstLetter(city));
        orderData.setVehicleType(Utils.capitalizeFirstLetter(vehicleType));
        orderData.setDeliveryFee(deliveryFee);
        WeatherData weatherData = getLastDataByCityFromWeatherComponent(city);
        orderData.setWeatherId(weatherData.getId());
        orderData.setTimestamp(Instant.now());

        saveOrderData(orderData);

        // Create a new OrderData object without weatherId
        return orderData;
    }


    public WeatherData getLastDataByCityFromWeatherComponent(String city) {
        return weatherDataComponent.getLastDataByCity(
                Utils.capitalizeFirstLetter(city)
        );
    }

    public OrderData getDeliveryFee(String city, String vehicleType) throws DeliveryFeeExceptionsList {

        String cityParam = city.toLowerCase(Locale.ROOT);
        String vehicleTypeParam = vehicleType.toLowerCase(Locale.ROOT);

        List<DeliveryFeeException> deliveryFeeExceptionsList = validateInputs(
                cityParam,
                vehicleTypeParam
        );

        switch (deliveryFeeExceptionsList.size()) {
            case 0:
                // No exceptions, do nothing
                break;
            case 1:
                // Throw a single DeliveryFeeException
                throw new DeliveryFeeException(deliveryFeeExceptionsList);
            default:
                // Throw a DeliveryFeeExceptionsList with all exceptions
                throw new DeliveryFeeExceptionsList(deliveryFeeExceptionsList);
        }

        // Calculate the delivery fee based on the city and vehicle type
        double deliveryFee = calculateDeliveryFee(
                cityParam,
                vehicleTypeParam
        );

        // Create OrderData object
        OrderData responseOrderData = createNewOrderData(
                cityParam,
                vehicleTypeParam,
                deliveryFee);

        return responseOrderData;
    }
}

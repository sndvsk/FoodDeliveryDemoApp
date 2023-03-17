package com.example.FoodDeliveryDemoApp.component;

import com.example.FoodDeliveryDemoApp.exception.DeliveryFeeException;
import com.example.FoodDeliveryDemoApp.model.OrderData;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.repository.OrderDataRepository;
import com.example.FoodDeliveryDemoApp.repository.WeatherDataRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Component
public class DeliveryFeeComponent {

    private final WeatherDataRepository weatherDataRepository;
    private final OrderDataRepository orderDataRepository;

    private final WeatherDataComponent weatherDataComponent;

    public DeliveryFeeComponent(WeatherDataRepository weatherDataRepository, OrderDataRepository orderDataRepository, WeatherDataComponent weatherDataComponent) {
        this.weatherDataRepository = weatherDataRepository;
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

/*    public double calculateWeatherConditionFee(String city, String vehicleType) {

        WeatherData weatherData = weatherDataRepository.findTopByStationName(city);

        double airTemperatureFee = 0;
        double windSpeedFee = 0;
        double weatherPhenomenonFee = 0;
        if (vehicleType.equals("bike") || vehicleType.equals("scooter")) {
            airTemperatureFee += calculateAirTemperatureFee(weatherData.getAirTemperature());
        }
        if (vehicleType.equals("bike")) {
            windSpeedFee += calculateWindSpeedFee(weatherData.getWindSpeed());
        }
        if (vehicleType.equals("bike") || vehicleType.equals("scooter")) {
            weatherPhenomenonFee += calculateWeatherPhenomenonFee(weatherData.getWeatherPhenomenon());
        }

        return airTemperatureFee + windSpeedFee + weatherPhenomenonFee;
    }*/

    public double calculateWeatherConditionFee(String city, String vehicleType) {
        WeatherData weatherData = weatherDataRepository.findTopByStationName(city);

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
    private static final Set<String> snowOrSleetWeatherPhenomenon = new HashSet<>(Arrays.asList(
            "Light sleet",
            "Moderate sleet",
            "Light snow shower",
            "Moderate snow shower",
            "Heavy snow shower",
            "Light snowfall",
            "Moderate snowfall",
            "Heavy snowfall",
            "Blowing snow",
            "Drifting snow"
    ));

    // ´static final´ to avoid recreating every time the method ´calculateWeatherPhenomenonFee´ is called
    private static final Set<String> rainWeatherPhenomenon = new HashSet<>(Arrays.asList(
            "Light shower",
            "Moderate shower",
            "Heavy shower",
            "Light rain",
            "Moderate rain",
            "Heavy rain"
    ));

    // ´static final´ to avoid recreating every time the method ´calculateWeatherPhenomenonFee´ is called
    private static final Set<String> dangerousWeatherPhenomenon = new HashSet<>(Arrays.asList(
            "Glaze",
            "Hail",
            "Thunder",
            "Thunderstorm"
    ));

    private double calculateWeatherPhenomenonFee(String weatherPhenomenon) {

        if (snowOrSleetWeatherPhenomenon.contains(weatherPhenomenon)) {
            return 1;
        }

        if (rainWeatherPhenomenon.contains(weatherPhenomenon)) {
            return 0.5;
        }
        if (dangerousWeatherPhenomenon.contains(weatherPhenomenon)) {
            throw new DeliveryFeeException("Usage of selected vehicle type is forbidden");
        }

        return 0;
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

        if (city == null || !validCityNames.contains(city.trim().toLowerCase())) {
            exceptionList.add(
                    createException(
                            String.format("City: %s argument is not supported.", city)));
        }

        if (vehicleType == null || !validVehicleTypes.contains(vehicleType.trim().toLowerCase())) {
            exceptionList.add(
                    createException(
                            String.format("Vehicle type: %s is not supported.", vehicleType)));
        }

        return exceptionList;
    }

    private DeliveryFeeException createException(String message) {
        return new DeliveryFeeException(message);
    }

    public OrderData createNewOrderData(String city, String vehicleType, double deliveryFee) {

        OrderData orderData = new OrderData();
        orderData.setCity(city);
        orderData.setVehicleType(vehicleType);
        orderData.setDeliveryFee(deliveryFee);
        WeatherData weatherData = weatherDataComponent.getLastDataByCity(city);
        orderData.setWeatherId(weatherData.getId());
        orderData.setTimestamp(Instant.now());

        saveOrderData(orderData);

        OrderData responseOrderData = new OrderData();
        responseOrderData.setCity(orderData.getCity());
        responseOrderData.setVehicleType(orderData.getVehicleType());
        responseOrderData.setDeliveryFee(orderData.getDeliveryFee());
        responseOrderData.setTimestamp(orderData.getTimestamp());

        // Create a new OrderData object without weatherId
        return responseOrderData;
    }
}

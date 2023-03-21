package com.example.FoodDeliveryDemoApp;

import com.example.FoodDeliveryDemoApp.component.DeliveryFeeComponent;
import com.example.FoodDeliveryDemoApp.component.WeatherDataComponent;
import com.example.FoodDeliveryDemoApp.exception.deliveryfee.DeliveryFeeException;
import com.example.FoodDeliveryDemoApp.exception.deliveryfee.DeliveryFeeExceptionsList;
import com.example.FoodDeliveryDemoApp.model.DeliveryFee;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.repository.DeliveryFeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeliveryFeeControllerTests {

    private DeliveryFeeComponent deliveryFeeComponent;

    @Mock
    private WeatherDataComponent weatherDataComponent;

    @Mock
    private DeliveryFeeRepository deliveryFeeRepository;

    private final Map<String, String> stationWmoCode = new HashMap<>() {{
        put("tallinn", "26038");
        put("tartu", "26242");
        put("pärnu", "41803");
    }};

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        deliveryFeeComponent = new DeliveryFeeComponent(deliveryFeeRepository, weatherDataComponent);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tallinn
      vehicleType = car
      weather = normal
      result = success
     */
    public void testCalculateAndSaveDeliveryFee_01_success() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "tallinn";
        String vehicleType = "car";

        Instant instant = Instant.parse("2023-03-20T12:15:00Z");
        //noinspection unused
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        WeatherData weatherData = new WeatherData();
        weatherData.setAirTemperature(10.0);
        weatherData.setWeatherPhenomenon("Clear");
        weatherData.setStationName(city);
        weatherData.setWmoCode(stationWmoCode.get(city.toLowerCase(Locale.ROOT)));
        weatherData.setWindSpeed(5.0);

        double regionalBaseFee = 4.0;
        double weatherConditionFee = 0.0;
        double deliveryFee = regionalBaseFee + weatherConditionFee;

        when(weatherDataComponent.getLastDataByCity(city))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataComponent, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataComponent);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tartu
      vehicleType = scooter
      weather = normal
      result = success
     */
    public void testCalculateAndSaveDeliveryFee_02_success() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "tartu";
        String vehicleType = "scooter";

        WeatherData weatherData = new WeatherData();
        weatherData.setAirTemperature(10.0);
        weatherData.setWeatherPhenomenon("Clear");
        weatherData.setStationName(city);
        weatherData.setWmoCode(stationWmoCode.get(city.toLowerCase(Locale.ROOT)));
        weatherData.setWindSpeed(5.0);

        double regionalBaseFee = 3.0;
        double weatherConditionFee = 0.0;
        double deliveryFee = regionalBaseFee + weatherConditionFee;

        when(weatherDataComponent.getLastDataByCity(city))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataComponent, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataComponent);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = pärnu
      vehicleType = bike
      weather = normal
      result = success
     */
    public void testCalculateAndSaveDeliveryFee_03_success() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "pärnu";
        String vehicleType = "bike";

        WeatherData weatherData = new WeatherData();
        weatherData.setAirTemperature(10.0);
        weatherData.setWeatherPhenomenon("Clear");
        weatherData.setStationName(city);
        weatherData.setWmoCode(stationWmoCode.get(city.toLowerCase(Locale.ROOT)));
        weatherData.setWindSpeed(5.0);

        double regionalBaseFee = 2.0;
        double weatherConditionFee = 0.0;
        double deliveryFee = regionalBaseFee + weatherConditionFee;

        when(weatherDataComponent.getLastDataByCity(city))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataComponent, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataComponent);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tallinn (mixed case)
      vehicleType = car (mixed case)
      weather = normal
      result = success
     */
    public void testCalculateAndSaveDeliveryFee_04_mixedCase_success() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "tAlLiNn";
        String vehicleType = "cAR";

        //city = city.toLowerCase(Locale.ROOT);

        WeatherData weatherData = new WeatherData();
        weatherData.setAirTemperature(10.0);
        weatherData.setWeatherPhenomenon("Clear");
        weatherData.setStationName(city);
        weatherData.setWmoCode(stationWmoCode.get(city.toLowerCase(Locale.ROOT)));
        weatherData.setWindSpeed(5.0);

        double regionalBaseFee = 4.0;
        double weatherConditionFee = 0.0;
        double deliveryFee = regionalBaseFee + weatherConditionFee;

        when(weatherDataComponent.getLastDataByCity(city.toLowerCase(Locale.ROOT)))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertTrue(city.equalsIgnoreCase(response.getCity()));
        assertTrue(vehicleType.equalsIgnoreCase(response.getVehicleType()));

        verify(weatherDataComponent, times(2)).getLastDataByCity(city.toLowerCase(Locale.ROOT));
        verifyNoMoreInteractions(weatherDataComponent);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tallinn
      vehicleType = bike
      weather = windSpeed > 20.0
      result = exception
     */
    public void testCalculateAndSaveDeliveryFee_05_bigWind_exception() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "tallinn";
        String vehicleType = "bike";

        WeatherData weatherData = new WeatherData();
        weatherData.setAirTemperature(-3.0);
        weatherData.setWeatherPhenomenon("Clear");
        weatherData.setStationName(city);
        weatherData.setWmoCode(stationWmoCode.get(city.toLowerCase(Locale.ROOT)));
        weatherData.setWindSpeed(21.0);

        when(weatherDataComponent.getLastDataByCity(city))
                .thenReturn(weatherData);

        List<DeliveryFeeException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeException("Usage of selected vehicle type is forbidden: wind speed too high")
        ));

        try {
            deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);
            //fail("Expected DeliveryFeeExceptionsList to be thrown");
        } catch (DeliveryFeeException e) {
            assertEquals(exceptionList.get(0).getMessage(), e.getLocalizedMessage());
        }

        verify(weatherDataComponent, times(1)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataComponent);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tallinn
      vehicleType = bike
      weather = weatherPhenomenon - thunder
      result = exception
     */
    public void testCalculateAndSaveDeliveryFee_06_thunder_exception() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "tallinn";
        String vehicleType = "bike";

        WeatherData weatherData = new WeatherData();
        weatherData.setAirTemperature(-3.0);
        weatherData.setWeatherPhenomenon("Thunder");
        weatherData.setStationName(city);
        weatherData.setWmoCode(stationWmoCode.get(city.toLowerCase(Locale.ROOT)));
        weatherData.setWindSpeed(15.0);

        when(weatherDataComponent.getLastDataByCity(city))
                .thenReturn(weatherData);

        List<DeliveryFeeException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeException("Usage of selected vehicle type is forbidden")
        ));

        try {
            deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);
            //fail("Expected DeliveryFeeExceptionsList to be thrown");
        } catch (DeliveryFeeException e) {
            assertEquals(exceptionList.get(0).getMessage(), e.getLocalizedMessage());
        }

        verify(weatherDataComponent, times(1)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataComponent);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tartu
      vehicleType = bike
      weather = airTemperature - -5 > x > -10,
                windSpeed - 10.0 < x < 20.0,
                weatherPhenomenon - snow related
      result = success
     */
    public void testCalculateAndSaveDeliveryFee_07_variousWeatherConditions_success() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "tartu";
        String vehicleType = "bike";

        WeatherData weatherData = new WeatherData();
        weatherData.setAirTemperature(-7.5);
        weatherData.setWeatherPhenomenon("Moderate snowfall");
        weatherData.setStationName(city);
        weatherData.setWmoCode(stationWmoCode.get(city.toLowerCase(Locale.ROOT)));
        weatherData.setWindSpeed(15.0);

        double regionalBaseFee = 2.5;
        double weatherConditionFee = 2.0;
        double deliveryFee = regionalBaseFee + weatherConditionFee;

        when(weatherDataComponent.getLastDataByCity(city))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataComponent, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataComponent);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tallinn
      vehicleType = scooter
      weather = airTemperature - x < -10,
                weatherPhenomenon - rain related
      result = success
     */
    public void testCalculateAndSaveDeliveryFee_08_withConditions_success() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "tallinn";
        String vehicleType = "scooter";

        WeatherData weatherData = new WeatherData();
        weatherData.setAirTemperature(-20.5);
        weatherData.setWeatherPhenomenon("Heavy rain");
        weatherData.setStationName(city);
        weatherData.setWmoCode(stationWmoCode.get(city.toLowerCase(Locale.ROOT)));
        weatherData.setWindSpeed(15.0);

        double regionalBaseFee = 3.5;
        double weatherConditionFee = 1.5;
        double deliveryFee = regionalBaseFee + weatherConditionFee;

        when(weatherDataComponent.getLastDataByCity(city))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataComponent, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataComponent);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = asd
      vehicleType = asd
      weather = normal
      result = exception
     */
    public void testCalculateAndSaveDeliveryFee_09_exception() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "asd";
        String vehicleType = "asd";

        List<DeliveryFeeException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeException(String.format("City: ´%s´ argument is invalid or not supported.", city)),
                new DeliveryFeeException(String.format("Vehicle type: ´%s´ argument is invalid or not supported.", vehicleType))
        ));

        assertThrows(DeliveryFeeExceptionsList.class, () ->
                deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType),
                "Expected exception not thrown");

        try {
            deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);
            fail("Expected DeliveryFeeExceptionsList to be thrown");
        } catch (DeliveryFeeExceptionsList e) {
            assertIterableEquals(exceptionList, e.getExceptions());
        }
    }
    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = ""
      vehicleType = ""
      weather = normal
      result = exception
     */
    public void testCalculateAndSaveDeliveryFee_10_exception() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "";
        String vehicleType = "";

        List<DeliveryFeeException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeException("Parameter city is empty."),
                new DeliveryFeeException("Parameter vehicle type is empty.")
        ));

        assertThrows(DeliveryFeeExceptionsList.class, () ->
                deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType),
                "Expected exception not thrown");

        try {
            deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);
            fail("Expected DeliveryFeeExceptionsList to be thrown");
        } catch (DeliveryFeeExceptionsList e) {
            assertIterableEquals(exceptionList, e.getExceptions());
        }
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = ""
      vehicleType = car
      weather = normal
      result = exception
     */
    public void testCalculateAndSaveDeliveryFee_11_exception() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "";
        String vehicleType = "car";

        List<DeliveryFeeException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeException("Parameter city is empty.")
        ));

        assertThrows(DeliveryFeeException.class, () ->
                deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType),
                "Expected exception not thrown");

        try {
            deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);
            fail("Expected DeliveryFeeException to be thrown");
        } catch (DeliveryFeeException e) {
            assertEquals(exceptionList.get(0).getMessage(), e.getLocalizedMessage());
        } catch (DeliveryFeeExceptionsList deliveryFeeExceptionsList) {
            fail("Expected DeliveryFeeException to be thrown");
        }
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tallinn
      vehicleType = ""
      weather = normal
      result = exception
     */
    public void testCalculateAndSaveDeliveryFee_12_exception() throws DeliveryFeeException, DeliveryFeeExceptionsList {
        String city = "tallinn";
        String vehicleType = "";

        List<DeliveryFeeException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeException("Parameter vehicle type is empty.")
        ));

        assertThrows(DeliveryFeeException.class, () ->
                deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType),
                "Expected exception not thrown");

        try {
            deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);
            fail("Expected DeliveryFeeException to be thrown");
        } catch (DeliveryFeeException e) {
            assertEquals(exceptionList.get(0).getMessage(), e.getLocalizedMessage());
        } catch (DeliveryFeeExceptionsList deliveryFeeExceptionsList) {
            fail("Expected DeliveryFeeException to be thrown");
        }
    }

}


package com.example.FoodDeliveryDemoApp;

import com.example.FoodDeliveryDemoApp.service.deliveryFee.DeliveryFeeService;
import com.example.FoodDeliveryDemoApp.service.deliveryFee.DeliveryFeeServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.airTemperatureRule.ExtraFeeAirTemperatureRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.weatherPhenomenonRule.ExtraFeeWeatherPhenomenonRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.windSpeedRule.ExtraFeeWindSpeedRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee.RegionalBaseFeeRuleServiceImpl;
import com.example.FoodDeliveryDemoApp.service.weatherData.WeatherDataServiceImpl;
import com.example.FoodDeliveryDemoApp.exception.deliveryFee.DeliveryFeeBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.deliveryFee.DeliveryFeeExceptionsList;
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

    private DeliveryFeeService deliveryFeeService;

    @Mock
    private WeatherDataServiceImpl weatherDataService;

    @Mock
    private ExtraFeeAirTemperatureRuleServiceImpl airTemperatureRuleService;

    @Mock
    private ExtraFeeWindSpeedRuleServiceImpl windSpeedRuleService;

    @Mock
    private ExtraFeeWeatherPhenomenonRuleServiceImpl weatherPhenomenonRuleService;

    @Mock
    private RegionalBaseFeeRuleServiceImpl baseFeeRuleService;

    @Mock
    private DeliveryFeeRepository deliveryFeeRepository;

    private final Map<String, Long> stationWmoCode = new HashMap<>() {{
        put("tallinn", 26038L);
        put("tartu", 26242L);
        put("pärnu", 41803L);
    }};

    public DeliveryFeeControllerTests() {
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        deliveryFeeService = new DeliveryFeeServiceImpl(
                deliveryFeeRepository,
                weatherDataService,
                airTemperatureRuleService,
                windSpeedRuleService,
                weatherPhenomenonRuleService,
                baseFeeRuleService);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tallinn
      vehicleType = car
      weather = normal
      result = success
     */
    public void testCalculateAndSaveDeliveryFee_01_success() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
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

        when(weatherDataService.getLastDataByCity(city, null))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType, null);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataService, times(2)).getLastDataByCity(city, null);
        verifyNoMoreInteractions(weatherDataService);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tartu
      vehicleType = scooter
      weather = normal
      result = success
     */
    public void testCalculateAndSaveDeliveryFee_02_success() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
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

        when(weatherDataService.getLastDataByCity(city))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataService, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataService);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = pärnu
      vehicleType = bike
      weather = normal
      result = success
     */
    public void testCalculateAndSaveDeliveryFee_03_success() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
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

        when(weatherDataService.getLastDataByCity(city))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataService, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataService);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tallinn (mixed case)
      vehicleType = car (mixed case)
      weather = normal
      result = success
     */
    public void testCalculateAndSaveDeliveryFee_04_mixedCase_success() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
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

        when(weatherDataService.getLastDataByCity(city.toLowerCase(Locale.ROOT)))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertTrue(city.equalsIgnoreCase(response.getCity()));
        assertTrue(vehicleType.equalsIgnoreCase(response.getVehicleType()));

        verify(weatherDataService, times(2)).getLastDataByCity(city.toLowerCase(Locale.ROOT));
        verifyNoMoreInteractions(weatherDataService);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tallinn
      vehicleType = bike
      weather = windSpeed > 20.0
      result = exception
     */
    public void testCalculateAndSaveDeliveryFee_05_bigWind_exception() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
        String city = "tallinn";
        String vehicleType = "bike";

        WeatherData weatherData = new WeatherData();
        weatherData.setAirTemperature(-3.0);
        weatherData.setWeatherPhenomenon("Clear");
        weatherData.setStationName(city);
        weatherData.setWmoCode(stationWmoCode.get(city.toLowerCase(Locale.ROOT)));
        weatherData.setWindSpeed(21.0);

        when(weatherDataService.getLastDataByCity(city))
                .thenReturn(weatherData);

        List<DeliveryFeeBadRequestException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeBadRequestException("Usage of selected vehicle type is forbidden: wind speed too high")
        ));

        try {
            deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType);
            //fail("Expected DeliveryFeeExceptionsList to be thrown");
        } catch (DeliveryFeeBadRequestException e) {
            assertEquals(exceptionList.get(0).getMessage(), e.getLocalizedMessage());
        }

        verify(weatherDataService, times(1)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataService);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = tallinn
      vehicleType = bike
      weather = weatherPhenomenon - thunder
      result = exception
     */
    public void testCalculateAndSaveDeliveryFee_06_thunder_exception() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
        String city = "tallinn";
        String vehicleType = "bike";

        WeatherData weatherData = new WeatherData();
        weatherData.setAirTemperature(-3.0);
        weatherData.setWeatherPhenomenon("Thunder");
        weatherData.setStationName(city);
        weatherData.setWmoCode(stationWmoCode.get(city.toLowerCase(Locale.ROOT)));
        weatherData.setWindSpeed(15.0);

        when(weatherDataService.getLastDataByCity(city))
                .thenReturn(weatherData);

        List<DeliveryFeeBadRequestException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeBadRequestException("Usage of selected vehicle type is forbidden")
        ));

        try {
            deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType);
            //fail("Expected DeliveryFeeExceptionsList to be thrown");
        } catch (DeliveryFeeBadRequestException e) {
            assertEquals(exceptionList.get(0).getMessage(), e.getLocalizedMessage());
        }

        verify(weatherDataService, times(1)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataService);
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
    public void testCalculateAndSaveDeliveryFee_07_variousWeatherConditions_success() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
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

        when(weatherDataService.getLastDataByCity(city))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataService, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataService);
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
    public void testCalculateAndSaveDeliveryFee_08_withConditions_success() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
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

        when(weatherDataService.getLastDataByCity(city))
                .thenReturn(weatherData);

        DeliveryFee response = deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataService, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataService);
    }

    @Test
    /*
      calculateAndSaveDeliveryFee method
      city = asd
      vehicleType = asd
      weather = normal
      result = exception
     */
    public void testCalculateAndSaveDeliveryFee_09_exception() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
        String city = "asd";
        String vehicleType = "asd";

        List<DeliveryFeeBadRequestException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeBadRequestException(String.format("City: ´%s´ argument is invalid or not supported.", city)),
                new DeliveryFeeBadRequestException(String.format("Vehicle type: ´%s´ argument is invalid or not supported.", vehicleType))
        ));

        assertThrows(DeliveryFeeExceptionsList.class, () ->
                deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType),
                "Expected exception not thrown");

        try {
            deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType);
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
    public void testCalculateAndSaveDeliveryFee_10_exception() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
        String city = "";
        String vehicleType = "";

        List<DeliveryFeeBadRequestException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeBadRequestException("Parameter city is empty."),
                new DeliveryFeeBadRequestException("Parameter vehicle type is empty.")
        ));

        assertThrows(DeliveryFeeExceptionsList.class, () ->
                deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType),
                "Expected exception not thrown");

        try {
            deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType);
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
    public void testCalculateAndSaveDeliveryFee_11_exception() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
        String city = "";
        String vehicleType = "car";

        List<DeliveryFeeBadRequestException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeBadRequestException("Parameter city is empty.")
        ));

        assertThrows(DeliveryFeeBadRequestException.class, () ->
                deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType),
                "Expected exception not thrown");

        try {
            deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType);
            fail("Expected DeliveryFeeException to be thrown");
        } catch (DeliveryFeeBadRequestException e) {
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
    public void testCalculateAndSaveDeliveryFee_12_exception() throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList {
        String city = "tallinn";
        String vehicleType = "";

        List<DeliveryFeeBadRequestException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeBadRequestException("Parameter vehicle type is empty.")
        ));

        assertThrows(DeliveryFeeBadRequestException.class, () ->
                deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType),
                "Expected exception not thrown");

        try {
            deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType);
            fail("Expected DeliveryFeeException to be thrown");
        } catch (DeliveryFeeBadRequestException e) {
            assertEquals(exceptionList.get(0).getMessage(), e.getLocalizedMessage());
        } catch (DeliveryFeeExceptionsList deliveryFeeExceptionsList) {
            fail("Expected DeliveryFeeException to be thrown");
        }
    }

}


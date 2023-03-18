package com.example.FoodDeliveryDemoApp;

import com.example.FoodDeliveryDemoApp.component.DeliveryFeeComponent;
import com.example.FoodDeliveryDemoApp.component.WeatherDataComponent;
import com.example.FoodDeliveryDemoApp.exception.DeliveryFeeException;
import com.example.FoodDeliveryDemoApp.exception.DeliveryFeeExceptionsList;
import com.example.FoodDeliveryDemoApp.model.OrderData;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.repository.OrderDataRepository;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeliveryFeeControllerTests {

    private DeliveryFeeComponent deliveryFeeComponent;

    @Mock
    private WeatherDataComponent weatherDataComponent;

    @Mock
    private OrderDataRepository orderDataRepository;

    private final Map<String, String> stationWmoCode = new HashMap<>() {{
        put("tallinn", "26038");
        put("tartu", "26242");
        put("pärnu", "41803");
    }};

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        deliveryFeeComponent = new DeliveryFeeComponent(orderDataRepository, weatherDataComponent);
    }

    @Test
    public void testGetDeliveryFeeTallinnCarNoWeatherFeeSuccess() throws DeliveryFeeExceptionsList {
        String city = "tallinn";
        String vehicleType = "car";

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

        OrderData response = deliveryFeeComponent.getDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataComponent, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataComponent);
    }

    @Test
    public void testGetDeliveryFeeTartuScooterNoWeatherFeeSuccess() throws DeliveryFeeExceptionsList {
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

        OrderData response = deliveryFeeComponent.getDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataComponent, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataComponent);
    }

    @Test
    public void testGetDeliveryFeeParnuBikeNoWeatherFeeSuccess() throws DeliveryFeeExceptionsList {
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

        OrderData response = deliveryFeeComponent.getDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());

        verify(weatherDataComponent, times(2)).getLastDataByCity(city);
        verifyNoMoreInteractions(weatherDataComponent);
    }

    @Test
    public void testGetDeliveryFeeTallinnCarNoWeatherFeeMixedCaseSuccess() throws DeliveryFeeExceptionsList {
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

        OrderData response = deliveryFeeComponent.getDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(deliveryFee, response.getDeliveryFee());
        assertTrue(city.equalsIgnoreCase(response.getCity()));
        assertTrue(vehicleType.equalsIgnoreCase(response.getVehicleType()));

        verify(weatherDataComponent, times(2)).getLastDataByCity(city.toLowerCase(Locale.ROOT));
        verifyNoMoreInteractions(weatherDataComponent);
    }

/*    @Test
    public void testGetDeliveryFeeWithDeliveryFeeException() {
        String city = "asd";
        String vehicleType = "asd";
        List<DeliveryFeeException> exceptionList = new ArrayList<>(List.of(
                new DeliveryFeeException(String.format("City: ´%s´ argument is invalid or not supported.", city)),
                new DeliveryFeeException(String.format("Vehicle type: ´%s´ argument is invalid or not supported.", vehicleType))
        ));

        assertThrows(DeliveryFeeExceptionsList.class, () -> {
            deliveryFeeComponent.getDeliveryFee(city, vehicleType);
        }, "Expected exception not thrown");

        try {
            deliveryFeeComponent.getDeliveryFee(city, vehicleType);
            fail("Expected DeliveryFeeExceptionsList to be thrown");
        } catch (DeliveryFeeExceptionsList e) {
            assertEquals(exceptionList, e.getExceptions());
        }
    }*/
}


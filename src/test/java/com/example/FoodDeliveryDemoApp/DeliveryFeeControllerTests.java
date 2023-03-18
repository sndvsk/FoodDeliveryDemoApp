package com.example.FoodDeliveryDemoApp;

import com.example.FoodDeliveryDemoApp.component.DeliveryFeeComponent;
import com.example.FoodDeliveryDemoApp.component.WeatherDataComponent;
import com.example.FoodDeliveryDemoApp.controller.DeliveryFeeController;
import com.example.FoodDeliveryDemoApp.exception.DeliveryFeeException;
import com.example.FoodDeliveryDemoApp.exception.DeliveryFeeExceptionsList;
import com.example.FoodDeliveryDemoApp.model.OrderData;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.repository.OrderDataRepository;
import com.example.FoodDeliveryDemoApp.repository.WeatherDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DeliveryFeeControllerTests {

    private DeliveryFeeComponent deliveryFeeComponent;

    @Mock
    private WeatherDataComponent weatherDataComponent;

    @Mock
    private OrderDataRepository orderDataRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        deliveryFeeComponent = new DeliveryFeeComponent(orderDataRepository, weatherDataComponent);
    }

    @Test
    public void testGetDeliveryFeeSuccess() throws DeliveryFeeExceptionsList {
        String city = "Tallinn";
        String vehicleType = "Car";

        WeatherData weatherData = new WeatherData();
        weatherData.setAirTemperature(10.0);
        weatherData.setWeatherPhenomenon("Clear");
        weatherData.setStationName("Tallinn");
        weatherData.setWmoCode("26038");
        weatherData.setWindSpeed(5.0);

        double regionalBaseFee = 4.0;
        double weatherConditionFee = 0.0;
        double deliveryFee = regionalBaseFee + weatherConditionFee;
/*

        when(deliveryFeeComponent.validateInputs(city.toLowerCase(), vehicleType.toLowerCase()))
                .thenReturn(exceptionList);

        when(deliveryFeeComponent.calculateRegionalBaseFee(city.toLowerCase(), vehicleType.toLowerCase()))
                .thenReturn(4.0);

        when(deliveryFeeComponent.calculateWeatherConditionFee(city.toLowerCase(), vehicleType.toLowerCase()))
                .thenReturn(0.0);

        when(deliveryFeeComponent.calculateDeliveryFee(city.toLowerCase(), vehicleType.toLowerCase()))
                .thenReturn(regionalBaseFee + weatherConditionFee);

        when(deliveryFeeComponent.createNewOrderData(city.toLowerCase(), vehicleType.toLowerCase(), deliveryFee))
                .thenReturn(orderData);
*/

        when(weatherDataComponent.getLastDataByCity(city))
                .thenReturn(weatherData);

        OrderData response = deliveryFeeComponent.getDeliveryFee(city, vehicleType);

        assertNotNull(response);

        assertEquals(4.0, response.getDeliveryFee());
        assertEquals(city, response.getCity());
        assertEquals(vehicleType, response.getVehicleType());
    }

    @Test
    public void testGetDeliveryFeeWithDeliveryFeeException() throws DeliveryFeeExceptionsList {
/*        String city = "Tallinn";
        String vehicleType = "car";
        List<DeliveryFeeException> exceptionList = List.of(
                new DeliveryFeeException("Exception 1"),
                new DeliveryFeeException("Exception 2")
        );

        when(deliveryFeeComponent.validateInputs(city.toLowerCase(), vehicleType.toLowerCase()))
                .thenReturn(exceptionList);

        try {
            deliveryFeeController.getDeliveryFee(city, vehicleType);
        } catch (DeliveryFeeExceptionsList e) {
            assertEquals(exceptionList, e.getExceptions());
            verify(deliveryFeeComponent).validateInputs(city.toLowerCase(), vehicleType.toLowerCase());
            verifyNoMoreInteractions(deliveryFeeComponent);
            return;
        }

        throw new AssertionError("Expected DeliveryFeeExceptionsList to be thrown.");
    }*/
    }
}


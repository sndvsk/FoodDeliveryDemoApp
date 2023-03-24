package com.example.FoodDeliveryDemoApp.service.deliveryFee;


import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomExceptionList;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.model.DeliveryFee;
import com.example.FoodDeliveryDemoApp.model.WeatherData;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.TreeMap;

public interface DeliveryFeeService {

    List<DeliveryFee> getAllDeliveryFees() throws CustomNotFoundException;

    DeliveryFee getDeliveryFeeById(Long id) throws CustomBadRequestException;

    DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType) throws CustomBadRequestException, CustomExceptionList;

    DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime) throws CustomBadRequestException, CustomExceptionList;

    Double calculateDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime);

    Double calculateRegionalBaseFee(String city, String vehicleType);

    Double calculateWeatherConditionFee(String city, String vehicleType, OffsetDateTime dateTime);

    double calculateAirTemperatureFee(double airTemperature);

    double calculateWindSpeedFee(Double windSpeed);

    Double calculateWeatherPhenomenonFee(String weatherPhenomenon);

    DeliveryFee createNewDeliveryFee(String city, String vehicleType, double deliveryFeePrice, OffsetDateTime dateTime);

    void saveDeliveryFee(DeliveryFee deliveryFee);

}

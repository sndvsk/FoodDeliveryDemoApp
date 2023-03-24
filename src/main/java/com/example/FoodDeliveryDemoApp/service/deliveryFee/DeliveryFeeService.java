package com.example.FoodDeliveryDemoApp.service.deliveryFee;


import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.model.DeliveryFee;

import java.time.OffsetDateTime;
import java.util.List;

public interface DeliveryFeeService {

    List<DeliveryFee> getAllDeliveryFees() throws CustomNotFoundException;

    DeliveryFee getDeliveryFeeById(Long id) throws CustomNotFoundException;

    DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType) throws CustomBadRequestException;

    DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime) throws CustomBadRequestException;

    Double calculateDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime);

    Double calculateRegionalBaseFee(String city, String vehicleType);

    Double calculateWeatherConditionFee(String city, String vehicleType, OffsetDateTime dateTime);

    double calculateAirTemperatureFee(double airTemperature) throws CustomBadRequestException;

    double calculateWindSpeedFee(Double windSpeed) throws CustomBadRequestException;

    Double calculateWeatherPhenomenonFee(String weatherPhenomenon) throws CustomBadRequestException;

    DeliveryFee createNewDeliveryFee(String city, String vehicleType, double deliveryFeePrice, OffsetDateTime dateTime);

    void saveDeliveryFee(DeliveryFee deliveryFee);

}

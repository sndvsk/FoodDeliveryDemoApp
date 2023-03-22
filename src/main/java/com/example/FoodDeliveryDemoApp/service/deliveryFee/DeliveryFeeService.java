package com.example.FoodDeliveryDemoApp.service.deliveryFee;


import com.example.FoodDeliveryDemoApp.exception.deliveryFee.DeliveryFeeBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.deliveryFee.DeliveryFeeExceptionsList;
import com.example.FoodDeliveryDemoApp.exception.deliveryFee.DeliveryFeeNotFoundException;
import com.example.FoodDeliveryDemoApp.model.DeliveryFee;

import java.time.OffsetDateTime;
import java.util.List;

public interface DeliveryFeeService {



    void saveDeliveryFee(DeliveryFee deliveryFee);

    Double calculateDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime);

    Double calculateRegionalBaseFee(String city, String vehicleType);

    Double calculateWeatherConditionFee(String city, String vehicleType, OffsetDateTime dateTime);

    double calculateAirTemperatureFee(double airTemperature);

    double calculateWindSpeedFee(Double windSpeed);

    Double calculateWeatherPhenomenonFee(String weatherPhenomenon);

    void validateInputs(String city, String vehicleType) throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList;

    DeliveryFeeBadRequestException createException(String message);

    DeliveryFee createNewDeliveryFee(String city, String vehicleType, double deliveryFeePrice, OffsetDateTime dateTime);

    DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType) throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList;

    DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime) throws DeliveryFeeBadRequestException, DeliveryFeeExceptionsList;

    DeliveryFee getDeliveryFeeById(Long id) throws DeliveryFeeBadRequestException;

    List<DeliveryFee> getAllDeliveryFees() throws DeliveryFeeNotFoundException;

}

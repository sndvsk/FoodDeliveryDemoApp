package com.example.FoodDeliveryDemoApp.service.DeliveryFee;


import com.example.FoodDeliveryDemoApp.exception.deliveryfee.DeliveryFeeException;
import com.example.FoodDeliveryDemoApp.exception.deliveryfee.DeliveryFeeExceptionsList;
import com.example.FoodDeliveryDemoApp.exception.deliveryfee.DeliveryFeeNotFoundException;
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

    void validateInputs(String city, String vehicleType) throws DeliveryFeeException, DeliveryFeeExceptionsList;

    DeliveryFeeException createException(String message);

    DeliveryFee createNewDeliveryFee(String city, String vehicleType, double deliveryFeePrice, OffsetDateTime dateTime);

    DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType) throws DeliveryFeeException, DeliveryFeeExceptionsList;

    DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime) throws DeliveryFeeException, DeliveryFeeExceptionsList;

    DeliveryFee getDeliveryFeeById(Long id) throws DeliveryFeeException;

    List<DeliveryFee> getAllDeliveryFees() throws DeliveryFeeNotFoundException;

}

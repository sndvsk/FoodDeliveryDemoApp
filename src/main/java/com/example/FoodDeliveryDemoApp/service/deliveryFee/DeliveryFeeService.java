package com.example.FoodDeliveryDemoApp.service.deliveryFee;


import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.domain.DeliveryFee;

import java.time.OffsetDateTime;
import java.util.List;

public interface DeliveryFeeService {

    List<DeliveryFee> getAllDeliveryFees() throws CustomNotFoundException;

    DeliveryFee getDeliveryFeeById(Long id) throws CustomNotFoundException;

    @SuppressWarnings("UnusedReturnValue")
    DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType) throws CustomBadRequestException;

    DeliveryFee calculateAndSaveDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime)
            throws CustomBadRequestException;

}

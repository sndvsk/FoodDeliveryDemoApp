package com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.service;


import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.dto.DeliveryFeeDTO;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.domain.DeliveryFee;

import java.time.OffsetDateTime;
import java.util.List;

public interface DeliveryFeeService {

    List<DeliveryFeeDTO> getAllDeliveryFees() throws CustomNotFoundException;

    DeliveryFeeDTO getDeliveryFeeById(Long id) throws CustomNotFoundException;

    @SuppressWarnings("UnusedReturnValue")
    DeliveryFeeDTO calculateAndSaveDeliveryFee(String city, String vehicleType) throws CustomBadRequestException;

    DeliveryFeeDTO calculateAndSaveDeliveryFee(String city, String vehicleType, OffsetDateTime dateTime)
            throws CustomBadRequestException;

}

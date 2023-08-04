package com.example.FoodDeliveryDemoApp.component.calculations.googleMaps.service;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.calculations.googleMaps.dto.GoogleDirectionResponse;

import java.util.List;

public interface ExternalDirectionsService {

    GoogleDirectionResponse getDirections(List<AddressDTO> addressDTOS);

}

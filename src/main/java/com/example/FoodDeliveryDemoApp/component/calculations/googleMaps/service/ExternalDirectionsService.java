package com.example.FoodDeliveryDemoApp.component.calculations.googleMaps.service;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.calculations.googleMaps.dto.DirectionDTO;

import java.util.List;

public interface ExternalDirectionsService {

    DirectionDTO getDirections(List<AddressDTO> addressDTOS);

}

package com.example.FoodDeliveryDemoApp.component.calculations.googleMaps.controller;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.dto.DeliveryFeeDTO;
import com.example.FoodDeliveryDemoApp.component.calculations.googleMaps.dto.DirectionDTO;
import com.example.FoodDeliveryDemoApp.component.calculations.googleMaps.service.ExternalDirectionsService;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/directions")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Directions API", description = "Endpoint for calculating directions")
public class ExternalDirectionsController {

    private final ExternalDirectionsService directionsService;

    public ExternalDirectionsController(ExternalDirectionsService directionsService) {
        this.directionsService = directionsService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectionDTO> getDirections(@RequestBody List<AddressDTO> addressDTOS) {

        DirectionDTO directions = directionsService.getDirections(addressDTOS);

        return new ResponseEntity<>(directions, HttpStatus.OK);
    }

}

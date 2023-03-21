package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.component.DeliveryFeeComponent;
import com.example.FoodDeliveryDemoApp.exception.deliveryfee.DeliveryFeeException;
import com.example.FoodDeliveryDemoApp.exception.deliveryfee.DeliveryFeeExceptionsList;
import com.example.FoodDeliveryDemoApp.exception.deliveryfee.DeliveryFeeNotFoundException;
import com.example.FoodDeliveryDemoApp.model.DeliveryFee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/delivery-fee")
@Tag(name = "Delivery Fee API", description = "Endpoint for calculating delivery fee")
public class DeliveryFeeController {

    private final DeliveryFeeComponent deliveryFeeComponent;

    public DeliveryFeeController(DeliveryFeeComponent deliveryFeeComponent) {
        this.deliveryFeeComponent = deliveryFeeComponent;
    }

    /**
     * Calculates and saves a delivery fee based on the specified city and vehicle type, and optionally the specified date and time into database.
     *
     * @param city the city to calculate the delivery fee for
     * @param vehicleType the type of vehicle to use for the delivery
     * @param dateTime the date and time to calculate the delivery fee for, or null to use the current date and time
     * @return a ResponseEntity containing a DeliveryFee object representing the calculated delivery fee, if successful
     * @throws DeliveryFeeException if there is an error while calculating or saving the delivery fee
     * @throws DeliveryFeeExceptionsList if there are multiple errors while calculating or saving the delivery fee
     */
    @PostMapping()
    @Operation(summary = "Calculate delivery fee based on city and vehicle type")
    public ResponseEntity<DeliveryFee> calculateDeliveryFee(
            @Parameter(name = "city", description = "City of delivery", example = "Tallinn")
            @RequestParam String city,
            @Parameter(name = "vehicleType", description = "Vehicle type for delivery", example = "Car")
            @RequestParam String vehicleType,
            @Parameter(name = "datetime", description = "Time for when delivery fee should be calculated. UTC time only(-2 of EET).", example = "2023-03-20T12:15:00Z")
            @RequestParam(required = false, name = "datetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) throws DeliveryFeeException, DeliveryFeeExceptionsList {

        DeliveryFee responseDeliveryFee;
        if (dateTime == null) {
            responseDeliveryFee = deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType);
        } else {
            responseDeliveryFee = deliveryFeeComponent.calculateAndSaveDeliveryFee(city, vehicleType, dateTime);
        }

        return new ResponseEntity<>(responseDeliveryFee, HttpStatus.CREATED);
    }

    /**
     * Retrieves a delivery fee calculation by ID, or all delivery fee calculations if the ID parameter is blank.
     *
     * @param id the ID of the delivery fee calculation to retrieve, or null to retrieve all delivery fee calculations
     * @return a ResponseEntity containing either a DeliveryFee object representing the requested delivery fee calculation if id is provided, or a list of DeliveryFee objects representing all delivery fee calculations if id is ont provided
     * @throws DeliveryFeeException if an error occurs while retrieving the delivery fee by id
     */
    @GetMapping()
    @Operation(summary = "Get a delivery fee calculation. If parameter id is blank get all delivery fee calculations.")
    public ResponseEntity<?> getExistingDeliveryFeeById(
            @Parameter(name = "id", description = "Id of delivery fee calculation")
            @RequestParam(required = false) Long id) throws DeliveryFeeException, DeliveryFeeNotFoundException {

        if (id != null) {
            DeliveryFee responseDeliveryFee = deliveryFeeComponent.getDeliveryFeeById(id);
            return new ResponseEntity<>(responseDeliveryFee, HttpStatus.OK);
        } else {
            List<DeliveryFee> responseDeliveryFee = deliveryFeeComponent.getAllDeliveryFees();
            return new ResponseEntity<>(responseDeliveryFee, HttpStatus.OK);
        }

    }

}

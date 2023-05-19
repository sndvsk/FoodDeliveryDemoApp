package com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.controller;

import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.service.DeliveryFeeService;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.domain.DeliveryFee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery-fee")
@Tag(name = "Delivery Fee API", description = "Endpoint for calculating delivery fee")
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    public DeliveryFeeController(DeliveryFeeService deliveryFeeService) {
        this.deliveryFeeService = deliveryFeeService;
    }

    /**
     * Calculates and saves a delivery fee based on the specified city and vehicle type,
     * and optionally the specified date and time into database.
     *
     * @param city the city to calculate the delivery fee for
     * @param vehicleType the type of vehicle to use for the delivery
     * @param dateTime the date and time to calculate the delivery fee for, or null to use the current date and time
     * @return a ResponseEntity containing a DeliveryFee object representing the calculated delivery fee, if successful
     * @throws CustomBadRequestException if there is an error while calculating or saving the delivery fee
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Calculate delivery fee based on city and vehicle type parameters")
    public ResponseEntity<DeliveryFee> calculateDeliveryFee(
            @Parameter(name = "city", description = "City of delivery", example = "Tallinn")
            @RequestParam String city,
            @Parameter(name = "vehicleType", description = "Vehicle type for delivery", example = "Car")
            @RequestParam String vehicleType,
            @Parameter(name = "datetime", description = "Time for when delivery fee should be calculated. " +
                    "Showed with a timezone offset of a server", example = "2023-03-22T12:15:00+02:00")
            @RequestParam(required = false, name = "datetime")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dateTime)
            throws CustomBadRequestException {

        DeliveryFee responseDeliveryFee = deliveryFeeService.calculateAndSaveDeliveryFee(city, vehicleType, dateTime);

        return new ResponseEntity<>(responseDeliveryFee, HttpStatus.CREATED);
    }

    /**
     * Retrieves all delivery fee calculations if the ID parameter is blank.
     *
     * @return a ResponseEntity containing a list of DeliveryFee objects representing all delivery fee calculations
     * @throws CustomNotFoundException if an error occurs because entry does not exist
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all delivery fee calculations")
    public ResponseEntity<List<DeliveryFee>> getAllExistingDeliveryFees() throws CustomNotFoundException {

        List<DeliveryFee> responseDeliveryFee = deliveryFeeService.getAllDeliveryFees();

        return new ResponseEntity<>(responseDeliveryFee, HttpStatus.OK);
    }

    /**
     * Retrieves a delivery fee calculation by id.
     *
     * @param id the ID of the delivery fee calculation to retrieve
     * @return a ResponseEntity containing a DeliveryFee object representing the requested delivery fee calculation
     * @throws CustomNotFoundException if an error occurs because entry does not exist
     */
    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a delivery fee calculation by id")
    public ResponseEntity<DeliveryFee> getExistingDeliveryFeeById(
            @Parameter(name = "id", description = "Id of delivery fee calculation")
            @PathVariable(name = "id", value = "id") Long id) throws CustomNotFoundException {

        DeliveryFee responseDeliveryFee = deliveryFeeService.getDeliveryFeeById(id);

        return new ResponseEntity<>(responseDeliveryFee, HttpStatus.OK);
    }

}

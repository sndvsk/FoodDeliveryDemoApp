package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.component.DeliveryFeeComponent;
import com.example.FoodDeliveryDemoApp.exception.DeliveryFeeExceptionsList;
import com.example.FoodDeliveryDemoApp.model.OrderData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery-fee")
@Tag(name = "Delivery Fee API", description = "Endpoint for calculating delivery fee")
public class DeliveryFeeController {

    private final DeliveryFeeComponent deliveryFeeComponent;

    public DeliveryFeeController(DeliveryFeeComponent deliveryFeeComponent) {
        this.deliveryFeeComponent = deliveryFeeComponent;
    }

    @GetMapping() //localhost:8080/delivery-fee?city=enter-city&vehicleType=enter-vehicle
    @Operation(summary = "Calculate delivery fee based on city and vehicle type")
    public ResponseEntity<OrderData> getDeliveryFee(
            @Parameter(name = "city", description = "City of delivery")
            @RequestParam String city,
            @Parameter(name = "vehicleType", description = "Vehicle type for delivery")
            @RequestParam String vehicleType) throws DeliveryFeeExceptionsList {

        OrderData responseOrderData = deliveryFeeComponent.getDeliveryFee(city, vehicleType);

        return new ResponseEntity<>(responseOrderData, HttpStatus.OK);
    }

}

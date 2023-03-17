package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.component.DeliveryFeeComponent;
import com.example.FoodDeliveryDemoApp.exception.DeliveryFeeException;
import com.example.FoodDeliveryDemoApp.model.OrderData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/delivery-fee")
public class DeliveryFeeController {

    private final DeliveryFeeComponent deliveryFeeComponent;

    public DeliveryFeeController(DeliveryFeeComponent deliveryFeeComponent) {
        this.deliveryFeeComponent = deliveryFeeComponent;
    }

    @GetMapping() //localhost:8080/delivery-fee?city=__enter-city__&vehicleType=__enter-vehicle__
    public ResponseEntity<?> getDeliveryFee(@RequestParam String city, @RequestParam String vehicleType) {

        String cityParam = city.toLowerCase(Locale.ROOT);
        String vehicleTypeParam = vehicleType.toLowerCase(Locale.ROOT);

        List<DeliveryFeeException> deliveryFeeExceptionsList = deliveryFeeComponent.validateInputs(
                cityParam,
                vehicleTypeParam
        );

        if (!deliveryFeeExceptionsList.isEmpty()) {
            throw new DeliveryFeeException(deliveryFeeExceptionsList);
        }

        // Calculate the delivery fee based on the city and vehicle type
        double deliveryFee = deliveryFeeComponent.calculateDeliveryFee(
                cityParam,
                vehicleTypeParam
        );

        // Create OrderData object
        OrderData responseOrderData = deliveryFeeComponent.createNewOrderData(
                city,
                vehicleType,
                deliveryFee);

        return new ResponseEntity<>(responseOrderData, HttpStatus.OK);
    }


}

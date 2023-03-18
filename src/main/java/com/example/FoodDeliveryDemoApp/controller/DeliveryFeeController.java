package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.component.DeliveryFeeComponent;
import com.example.FoodDeliveryDemoApp.exception.DeliveryFeeException;
import com.example.FoodDeliveryDemoApp.exception.DeliveryFeeExceptionsList;
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
    public ResponseEntity<?> getDeliveryFee(@RequestParam String city, @RequestParam String vehicleType) throws DeliveryFeeExceptionsList {

        String cityParam = city.toLowerCase(Locale.ROOT);
        String vehicleTypeParam = vehicleType.toLowerCase(Locale.ROOT);

        List<DeliveryFeeException> deliveryFeeExceptionsList = deliveryFeeComponent.validateInputs(
                cityParam,
                vehicleTypeParam
        );

        switch (deliveryFeeExceptionsList.size()) {
            case 0:
                // No exceptions, do nothing
                break;
            case 1:
                // Throw a single DeliveryFeeException
                throw new DeliveryFeeException(deliveryFeeExceptionsList);
            default:
                // Throw a DeliveryFeeExceptionsList with all exceptions
                throw new DeliveryFeeExceptionsList(deliveryFeeExceptionsList);
        }

        // Calculate the delivery fee based on the city and vehicle type
        double deliveryFee = deliveryFeeComponent.calculateDeliveryFee(
                cityParam,
                vehicleTypeParam
        );

        // Create OrderData object
        OrderData responseOrderData = deliveryFeeComponent.createNewOrderData(
                cityParam,
                vehicleTypeParam,
                deliveryFee);

        return new ResponseEntity<>(responseOrderData, HttpStatus.OK);
    }


}

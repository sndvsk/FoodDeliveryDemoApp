package com.example.FoodDeliveryDemoApp.exception.deliveryfee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DeliveryFeeNotFoundException extends RuntimeException {

    public DeliveryFeeNotFoundException(String message) {
        super(message);
    }

}

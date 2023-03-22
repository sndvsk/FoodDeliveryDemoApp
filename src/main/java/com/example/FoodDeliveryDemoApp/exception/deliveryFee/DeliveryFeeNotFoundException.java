package com.example.FoodDeliveryDemoApp.exception.deliveryFee;

import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DeliveryFeeNotFoundException extends NotFoundException {

    // todo rename to CustomNotFoundException

    public DeliveryFeeNotFoundException(String message) {
        super(message);
    }

}

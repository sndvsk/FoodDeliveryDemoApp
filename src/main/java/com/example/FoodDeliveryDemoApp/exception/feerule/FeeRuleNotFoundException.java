package com.example.FoodDeliveryDemoApp.exception.feerule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FeeRuleNotFoundException extends RuntimeException {

    public FeeRuleNotFoundException(String message) {
        super(message);
    }

}

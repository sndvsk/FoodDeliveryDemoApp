package com.example.FoodDeliveryDemoApp.exception.feerule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FeeRuleBadRequestException extends IllegalArgumentException {

    public FeeRuleBadRequestException(String message) {
        super(message);
    }

}

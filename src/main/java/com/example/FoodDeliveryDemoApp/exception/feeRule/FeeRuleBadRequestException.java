package com.example.FoodDeliveryDemoApp.exception.feeRule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FeeRuleBadRequestException extends IllegalArgumentException {

    // todo delete and swap all usages to CustomBadRequestException

    public FeeRuleBadRequestException(String message) {
        super(message);
    }

}

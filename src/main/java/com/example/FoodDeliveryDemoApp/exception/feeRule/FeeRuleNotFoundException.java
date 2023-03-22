package com.example.FoodDeliveryDemoApp.exception.feeRule;

import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FeeRuleNotFoundException extends NotFoundException {

    // todo delete and swap all usages to CustomNotFoundException

    public FeeRuleNotFoundException(String message) {
        super(message);
    }

}

package com.example.FoodDeliveryDemoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomInternalServerError extends RuntimeException {

    public CustomInternalServerError(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public CustomInternalServerError(String message, Throwable cause) {
        super(message, cause);
    }
}
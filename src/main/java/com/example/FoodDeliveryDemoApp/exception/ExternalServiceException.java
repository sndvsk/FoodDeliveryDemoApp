package com.example.FoodDeliveryDemoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Error in external service")
public class ExternalServiceException extends RuntimeException {

    public ExternalServiceException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
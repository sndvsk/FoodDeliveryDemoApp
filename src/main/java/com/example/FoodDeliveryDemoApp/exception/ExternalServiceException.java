package com.example.FoodDeliveryDemoApp.exception;

public class ExternalServiceException extends RuntimeException {

    public ExternalServiceException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
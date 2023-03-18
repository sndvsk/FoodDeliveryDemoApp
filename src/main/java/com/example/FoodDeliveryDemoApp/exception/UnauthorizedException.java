package com.example.FoodDeliveryDemoApp.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}

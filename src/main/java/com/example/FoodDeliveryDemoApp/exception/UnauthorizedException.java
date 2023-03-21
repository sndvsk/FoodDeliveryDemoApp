package com.example.FoodDeliveryDemoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="Unauthorized in external service")
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}

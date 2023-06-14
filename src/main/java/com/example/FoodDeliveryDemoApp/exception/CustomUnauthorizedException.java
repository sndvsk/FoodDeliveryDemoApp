package com.example.FoodDeliveryDemoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class CustomUnauthorizedException extends BadCredentialsException {
    public CustomUnauthorizedException(String msg) {
        super(msg);
    }
}

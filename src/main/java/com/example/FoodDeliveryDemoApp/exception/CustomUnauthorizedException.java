package com.example.FoodDeliveryDemoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class CustomUnauthorizedException extends AuthenticationException {

    public CustomUnauthorizedException(String msg) {
        super(msg);
    }

    public String getMessage() {
        return super.getMessage();
    }
}

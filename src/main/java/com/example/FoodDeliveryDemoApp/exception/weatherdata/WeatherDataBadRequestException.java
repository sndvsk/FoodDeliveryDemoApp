package com.example.FoodDeliveryDemoApp.exception.weatherdata;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WeatherDataBadRequestException extends IllegalArgumentException {

    public WeatherDataBadRequestException(String message) {
        super(message);
    }

}

package com.example.FoodDeliveryDemoApp.exception.weatherData;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WeatherDataBadRequestException extends IllegalArgumentException {

    // todo delete and swap all usages to CustomBadRequestException

    public WeatherDataBadRequestException(String message) {
        super(message);
    }

}

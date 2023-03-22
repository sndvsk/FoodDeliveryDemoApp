package com.example.FoodDeliveryDemoApp.exception.weatherData;

import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WeatherDataNotFoundException extends NotFoundException {

    // todo delete and swap all usages to CustomNotFoundException

    public WeatherDataNotFoundException(String message) {
        super(message);
    }

}

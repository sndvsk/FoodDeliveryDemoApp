package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.component.WeatherDataComponent;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/get-weather-from-eea")
@Tag(name = "Weather Data from Estonian Environment Agency API", description = "Endpoint for getting weather data from Estonian Environment Agency.")
public class WeatherDataFromEEAController {

    private final WeatherDataComponent weatherDataComponent;

    @Autowired
    public WeatherDataFromEEAController(WeatherDataComponent weatherDataComponent) {
        this.weatherDataComponent = weatherDataComponent;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get latest weather data from Estonian Environment Agency for Tallinn, Tartu and Pärnu")
    public ResponseEntity<List<WeatherData>> getWeatherObservations() throws JAXBException {

        List<WeatherData> lastWeatherData = weatherDataComponent.getWeatherDataFromServiceAndSave();

        return new ResponseEntity<>(lastWeatherData, HttpStatus.OK);
    }

}

package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.component.WeatherDataComponent;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
@Tag(name = "Weather Data API", description = "Endpoint for getting weather data from database.")
public class WeatherDataController {

    private final WeatherDataComponent weatherDataComponent;

    @Autowired
    public WeatherDataController(WeatherDataComponent weatherDataComponent) {
        this.weatherDataComponent = weatherDataComponent;
    }

    /**
     * Retrieves the latest weather data for Tallinn, Tartu and/or Pärnu, based on the provided cities parameter from database.
     *
     * @param cities a string containing a comma-separated list of city names to retrieve weather data for, or blank to retrieve data for all 3 cities
     * @return a ResponseEntity containing a list of WeatherData objects representing the latest weather observations for the requested cities
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get latest weather data for Tallinn, Tartu and/or Pärnu")
    public ResponseEntity<List<WeatherData>> getWeatherObservations(
            @Parameter(name = "cities",
                    description = "List of cities divided by comma without whitespaces example: ´tallinn,pärnu´. Blank parameter returns all 3 cities.")
            @RequestParam(required = false) String cities) {

        List<WeatherData> lastWeatherData = weatherDataComponent.getWeatherDataFromRepository(cities);

        return new ResponseEntity<>(lastWeatherData, HttpStatus.OK);
    }

}

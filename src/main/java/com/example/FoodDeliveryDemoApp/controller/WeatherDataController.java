package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.component.WeatherDataComponent;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.service.WeatherDataService;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherDataController {

    private final WeatherDataService weatherDataService;

    private final WeatherDataComponent weatherDataComponent;

    @Autowired
    public WeatherDataController(WeatherDataService weatherDataService, WeatherDataComponent weatherDataComponent) {
        this.weatherDataService = weatherDataService;
        this.weatherDataComponent = weatherDataComponent;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WeatherData>> getWeatherObservations() throws JAXBException {
        String response = weatherDataService.retrieveWeatherObservations();
        List<WeatherData> weatherData = weatherDataComponent.convertStationsToWeatherData(
                weatherDataComponent.filterResponse(response));
        weatherDataComponent.saveWeatherData(weatherData);
        List<WeatherData> lastWeatherDataForAllCities = weatherDataComponent.getLastWeatherDataForAllCities();

        List<WeatherData> lastWeatherDataForAllCitiesWithoutIds = weatherDataComponent.removeIdsFromLastDataForAllCities(lastWeatherDataForAllCities);

        return new ResponseEntity<>(lastWeatherDataForAllCitiesWithoutIds, HttpStatus.OK);
    }
}
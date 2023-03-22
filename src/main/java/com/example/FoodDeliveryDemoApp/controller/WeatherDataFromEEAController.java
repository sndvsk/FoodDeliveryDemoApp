package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.service.externalWeatherData.ExternalWeatherDataService;
import com.example.FoodDeliveryDemoApp.service.weatherData.WeatherDataServiceImpl;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/get-weather-from-eea")
@Tag(name = "Weather Data from Estonian Environment Agency API", description = "Endpoint for getting weather data from Estonian Environment Agency")
public class WeatherDataFromEEAController {

    private final WeatherDataServiceImpl weatherDataService;

    private final ExternalWeatherDataService externalWeatherDataService;

    @Autowired
    public WeatherDataFromEEAController(WeatherDataServiceImpl weatherDataService, ExternalWeatherDataService externalWeatherDataService) {
        this.weatherDataService = weatherDataService;
        this.externalWeatherDataService = externalWeatherDataService;
    }

    /**
     * Retrieves, filters and saves the latest weather data from the Estonian Environment Agency for Tallinn, Tartu and Pärnu to database.
     *
     * @return a ResponseEntity containing a list of WeatherData objects representing the latest weather observations
     * @throws JAXBException if there is an error while parsing the weather data from the service
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save latest weather data from Estonian Environment Agency for Tallinn, Tartu and Pärnu to database")
    public ResponseEntity<List<WeatherData>> getAndSaveWeatherObservations() throws JAXBException {

        List<WeatherData> lastWeatherData = weatherDataService.getAndSaveWeatherDataFromService();

        return new ResponseEntity<>(lastWeatherData, HttpStatus.OK);
    }

    @GetMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "Get latest weather data from Estonian Environment Agency")
    public ResponseEntity<String> getWeatherDataFromServiceXML() {

        String response = externalWeatherDataService.retrieveWeatherObservations();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

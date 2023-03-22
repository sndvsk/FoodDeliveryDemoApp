package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.exception.weatherdata.WeatherDataNotFoundException;
import com.example.FoodDeliveryDemoApp.service.WeatherData.WeatherDataService;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather Data API", description = "Endpoint for getting weather data from database.")
public class WeatherDataController {

    private final WeatherDataService weatherDataService;

    @Autowired
    public WeatherDataController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    /**
     * Retrieves the latest weather data for Tallinn, Tartu and Pärnu from external weather API.
     *
     * @return a ResponseEntity containing a list of WeatherData objects representing the latest weather observations for all cities
     */
    @GetMapping(path= "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get latest weather data from Estonian Environment Agency for Tallinn, Tartu and Pärnu")
/*    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Weather data retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No weather data found for the provided cities", content = @Content)})*/
    public ResponseEntity<List<WeatherData>> getWeatherObservations() throws JAXBException {

        List<WeatherData> lastWeatherData = weatherDataService.getWeatherDataFromService();

        return new ResponseEntity<>(lastWeatherData, HttpStatus.OK);
    }

    /**
     * Retrieves the latest weather data for Tallinn, Tartu and/or Pärnu, based on the provided cities parameter from database.
     *
     * @param cities a string containing a comma-separated list of city names to retrieve weather data for, or blank to retrieve data for all 3 cities
     * @return a ResponseEntity containing a list of WeatherData objects representing the latest weather observations for the requested cities
     */
    @GetMapping(path ="/cities/{cities}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get latest weather data for Tallinn, Tartu and/or Pärnu")
    public ResponseEntity<List<WeatherData>> getWeatherDataFromService(
            @PathVariable(value = "cities") String cities) {

        List<WeatherData> lastWeatherData = weatherDataService.getWeatherDataFromRepository(cities);

        return new ResponseEntity<>(lastWeatherData, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get weather data by id.")
    public ResponseEntity<WeatherData> getWeatherDataById(
            @Parameter(name = "id", description = "Id of weather data", in = ParameterIn.PATH, example = "1" )
            @PathVariable(value = "id") Long weatherId) throws WeatherDataNotFoundException {

        WeatherData weatherData = weatherDataService.getWeatherDataById(weatherId);

        return new ResponseEntity<>(weatherData, HttpStatus.OK);
    }

    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get weather data by id.")
    public ResponseEntity<WeatherData> patchWeatherDataById(
            @Parameter(name = "airTemperature", description = "Air temperature", example = "10.0" )
            @RequestParam(required = false) Double airTemperature,
            @Parameter(name = "windSpeed", description = "Wind speed", example = "5.0" )
            @RequestParam(required = false) Double windSpeed,
            @Parameter(name = "weatherPhenomenon", description = "Weather phenomenon ", example = "Clear" )
            @RequestParam(required = false) String weatherPhenomenon,
            @Parameter(name = "id", description = "Id of weather data", in = ParameterIn.PATH, example = "1" )
            @PathVariable(value = "id") Long weatherId) throws WeatherDataNotFoundException {

        WeatherData weatherData = weatherDataService.patchWeatherDataById(weatherId, airTemperature, windSpeed, weatherPhenomenon);

        return new ResponseEntity<>(weatherData, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get weather data by id.")
    public ResponseEntity<String> deleteWeatherDataById(
            @Parameter(name = "id", description = "Id of weather data", in = ParameterIn.PATH, example = "1" )
            @PathVariable(value = "id") Long weatherId) throws WeatherDataNotFoundException {

        String response = weatherDataService.deleteWeatherDataById(weatherId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

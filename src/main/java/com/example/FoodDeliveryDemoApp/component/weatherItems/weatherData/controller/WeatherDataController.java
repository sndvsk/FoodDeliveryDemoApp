package com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.controller;

import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.service.WeatherDataService;
import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.domain.WeatherData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.JAXBException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@SuppressWarnings("DefaultAnnotationParam")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/weather")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Weather Data API", description = "Endpoint for getting weather data from database")
public class WeatherDataController {

    private final WeatherDataService weatherDataService;

    public WeatherDataController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    /**
     * Retrieves the latest weather data for all supported cities from external weather API.
     *
     * @return a ResponseEntity containing a list of WeatherData objects representing the latest weather observations
     * for all cities
     * @throws JAXBException if there is an error parsing the XML request body
     */
    @GetMapping(path= "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get latest weather data from Estonian Environment Agency for Tallinn, Tartu and Pärnu")
    public ResponseEntity<List<WeatherData>> getWeatherDataForAllSupportedCities() throws JAXBException {

        List<WeatherData> lastWeatherData = weatherDataService.getWeatherDataFromExternalService();

        return new ResponseEntity<>(lastWeatherData, HttpStatus.OK);
    }

    /**
     * Retrieves the latest weather data for provided cities parameter from database.
     *
     * @param cities a string containing a comma-separated list of city names to retrieve weather data for
     * @return a ResponseEntity containing a list of WeatherData objects representing the latest weather observations
     * for the requested cities
     */
    @GetMapping(path ="/cities/{cities}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get latest weather data for supported cities")
    public ResponseEntity<List<WeatherData>> getWeatherDataForSelectedCities(
            @PathVariable(value = "cities") String cities) {

        List<WeatherData> lastWeatherData = weatherDataService.getLastWeatherDataForAllCities(cities);

        return new ResponseEntity<>(lastWeatherData, HttpStatus.OK);
    }

    /**
     * Retrieve all weather data.
     *
     * @return ResponseEntity with List of WeatherData and HttpStatus.OK if successful
     * @throws CustomNotFoundException if weather data is not found
     */
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all weather data")
    public ResponseEntity<List<WeatherData>> getAllWeatherData() throws CustomNotFoundException {

        List<WeatherData> weatherData = weatherDataService.getAllWeatherData();

        return new ResponseEntity<>(weatherData, HttpStatus.OK);
    }

    /**
     * Add weather data.
     * @param stationName station name
     * @param wmoCode station wmo code
     * @param airTemperature air temperature
     * @param windSpeed wind speed
     * @param weatherPhenomenon weather phenomenon
     * @param dateTime yime for when weather data should be added. Showed with a timezone offset of a server
     * @return ResponseEntity with WeatherData and HttpStatus.OK if successful
     * @throws CustomNotFoundException if the weather data with the specified if does not exist
     * @throws JAXBException if there is an error parsing the XML request body
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add weather data")
    public ResponseEntity<WeatherData> addWeatherData(
            @Parameter(name = "stationName", description = "Station name", example = "")
            @RequestParam String stationName,
            @Parameter(name = "wmoCode", description = "Station wmo code", example = "")
            @RequestParam(required = false) Long wmoCode,
            @Parameter(name = "airTemperature", description = "Air temperature", example = "")
            @RequestParam Double airTemperature,
            @Parameter(name = "windSpeed", description = "Wind speed", example = "")
            @RequestParam Double windSpeed,
            @Parameter(name = "weatherPhenomenon", description = "Weather phenomenon", example = "")
            @RequestParam String weatherPhenomenon,
            @Parameter(name = "time", description = "Time for when weather data should be added. " +
                    "Showed with a timezone offset of a server", example = "2023-03-22T12:15:00+02:00")
            @RequestParam(required = false, name = "time")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dateTime)
            throws CustomNotFoundException, JAXBException {

        WeatherData weatherData = weatherDataService.addWeatherData(
                stationName, wmoCode, airTemperature, windSpeed, weatherPhenomenon, dateTime);

        return new ResponseEntity<>(weatherData, HttpStatus.CREATED);
    }

    /**
     * Retrieve weather data by id.
     *
     * @param weatherId id of weather data
     * @return ResponseEntity with WeatherData and HttpStatus.OK if successful
     * @throws CustomNotFoundException if the weather data with the specified if does not exist
     */
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get weather data by id")
    public ResponseEntity<WeatherData> getWeatherDataById(
            @Parameter(name = "id", description = "Id of weather data", in = ParameterIn.PATH, example = "" )
            @PathVariable(value = "id") Long weatherId) throws CustomNotFoundException {

        WeatherData weatherData = weatherDataService.getWeatherDataById(weatherId);

        return new ResponseEntity<>(weatherData, HttpStatus.OK);
    }

    /**
     * Update weather data by id.
     *
     * @param airTemperature air temperature
     * @param windSpeed wind speed
     * @param weatherPhenomenon weather phenomenon
     * @param weatherId id of weather data
     * @return ResponseEntity with WeatherData and HttpStatus.OK if successful
     * @throws CustomNotFoundException if the weather data with the specified if does not exist
     */
    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Patch weather data by id")
    public ResponseEntity<WeatherData> patchWeatherDataById(
            @Parameter(name = "airTemperature", description = "Air temperature", example = "" )
            @RequestParam(required = false) Double airTemperature,
            @Parameter(name = "windSpeed", description = "Wind speed", example = "" )
            @RequestParam(required = false) Double windSpeed,
            @Parameter(name = "weatherPhenomenon", description = "Weather phenomenon", example = "" )
            @RequestParam(required = false) String weatherPhenomenon,
            @Parameter(name = "id", description = "Id of weather data", in = ParameterIn.PATH, example = "" )
            @PathVariable(value = "id") Long weatherId) throws CustomNotFoundException {

        WeatherData weatherData = weatherDataService.patchWeatherDataById(
                weatherId, airTemperature, windSpeed, weatherPhenomenon);

        return new ResponseEntity<>(weatherData, HttpStatus.OK);
    }

    /**
     * Deletes a weather data by its id.
     *
     * @param id the id of the weather data to be deleted
     * @return a ResponseEntity containing a String message with the result of the deletion process
     * @throws CustomNotFoundException if the weather data with the specified if does not exist
     */
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete weather data by id")
    public ResponseEntity<String> deleteWeatherDataById(
            @Parameter(name = "id", description = "Id of weather data", in = ParameterIn.PATH, example = "" )
            @PathVariable(value = "id") Long id) throws CustomNotFoundException {

        String response = weatherDataService.deleteWeatherDataById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

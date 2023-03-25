package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.dto.WeatherDataDTO;
import com.example.FoodDeliveryDemoApp.service.externalWeatherData.ExternalWeatherDataService;
import com.example.FoodDeliveryDemoApp.service.weatherData.WeatherDataService;
import com.example.FoodDeliveryDemoApp.service.weatherData.WeatherDataServiceImpl;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import java.util.TreeMap;

@RestController
@RequestMapping("/api/get-weather-from-eea")
@Tag(name = "Weather Data from Estonian Environment Agency API", description = "Endpoint for getting weather data from Estonian Environment Agency")
public class ExternalWeatherDataController {

    private final WeatherDataService weatherDataService;

    private final ExternalWeatherDataService externalWeatherDataService;

    @Autowired
    public ExternalWeatherDataController(WeatherDataService weatherDataService, ExternalWeatherDataService externalWeatherDataService) {
        this.weatherDataService = weatherDataService;
        this.externalWeatherDataService = externalWeatherDataService;
    }

    /**
     * Retrieves, filters and saves the latest weather data from the Estonian Environment Agency for supported cities to database.
     *
     * @return a ResponseEntity containing a list of WeatherData objects representing the latest weather observations
     * @throws JAXBException if there is an error while parsing the weather data from the service
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save latest weather data from Estonian Environment Agency for Tallinn, Tartu and Pärnu to database")
    public ResponseEntity<List<WeatherData>> getAndSaveWeatherObservations() throws JAXBException {

        List<WeatherData> lastWeatherData = weatherDataService.getAndSaveWeatherDataFromExternalService();

        return new ResponseEntity<>(lastWeatherData, HttpStatus.OK);
    }

    /**
     * Retrieves the latest weather data from the Estonian Environment Agency.
     *
     * @return a XML String containing a list of WeatherDataDTO.Observations objects representing the latest weather observations
     */
    @GetMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @Operation(summary = "Get latest weather data from Estonian Environment Agency")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Weather data from external API retrieved successfully",
                    content = @Content(
                            mediaType = "application/xml",
                            array = @ArraySchema(schema = @Schema(implementation = WeatherDataDTO.Observations.class))))
    })
    public ResponseEntity<String> getWeatherDataFromServiceXML() {

        String response = externalWeatherDataService.retrieveWeatherObservations();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieve all possible station names and wmo code from external weather API.
     *
     * @return the TreeMap<String, Long> containing all station names and their respective wmo codes
     * @throws JAXBException if there is an error while parsing the weather data from the service
     */
    @GetMapping(path = "/stations", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all station names and its wmo codes from Estonian Environment Agency")
    public ResponseEntity<TreeMap<String, Long>> getPossibleStationNamesFromServiceXML() throws JAXBException {

        TreeMap<String, Long> response = externalWeatherDataService.getPossibleStationNamesAndCodes();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
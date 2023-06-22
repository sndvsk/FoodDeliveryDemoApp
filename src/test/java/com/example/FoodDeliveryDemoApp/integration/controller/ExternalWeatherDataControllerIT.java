package com.example.FoodDeliveryDemoApp.integration.controller;

import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.domain.WeatherData;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
public class ExternalWeatherDataControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Value("${host.url.test}")
    private String hostUrl;

    private final String apiUrl = "/api/v1/get-weather-from-eea";

    final HttpHeaders headers = new HttpHeaders();

    @Test
    @Order(1)
    void testGetAndSaveWeatherObservations() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<List<WeatherData>> response = restTemplate.exchange(
                hostUrl + port + apiUrl,
                HttpMethod.POST, entity, new ParameterizedTypeReference<>() {
                }
        );
        List<WeatherData> weatherDataList = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        // Assert that the API returns a non-empty list of weather observations
        assertNotNull(weatherDataList);
        assertTrue(weatherDataList.size() > 0);

        // Assert that each weather observation has the required fields
        for (WeatherData weatherData : weatherDataList) {
            assertNotNull(weatherData.getStationName());
            assertNotNull(weatherData.getWmoCode());
            assertNotNull(weatherData.getAirTemperature());
            assertNotNull(weatherData.getWindSpeed());
            assertNotNull(weatherData.getWeatherPhenomenon());
            assertNotNull(weatherData.getRest_timestamp());
        }
    }

    @Test
    @Order(2)
    public void testGetWeatherDataFromServiceXML() {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                hostUrl + port + apiUrl + "/xml",
                HttpMethod.GET, entity, String.class
        );

        // Validate the response is a valid XML string
        String xmlResponse = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(xmlResponse);

    }

    @Test
    @Order(3)
    void testGetPossibleStationNamesFromServiceXML() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<TreeMap<String, Long>> response = restTemplate.exchange(
                hostUrl + port + apiUrl + "/stations",
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                }
        );
        TreeMap<String, Long> stationNamesAndCodes = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        // Assert that the API returns a non-empty map of station names and codes
        assertNotNull(stationNamesAndCodes);
        assertTrue(stationNamesAndCodes.size() > 0);

        // Assert that each station has a non-empty name and code
        for (String stationName : stationNamesAndCodes.keySet()) {
            assertNotNull(stationName);
            assertNotNull(stationNamesAndCodes.get(stationName));
        }
    }

}
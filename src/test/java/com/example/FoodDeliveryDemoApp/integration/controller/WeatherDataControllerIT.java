package com.example.FoodDeliveryDemoApp.integration.controller;

import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.domain.WeatherData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
//import java.time.Instant;
//import java.time.OffsetDateTime;
//import java.time.ZoneId;
//import java.time.ZoneOffset;
//import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-integration.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Profile("test")
public class WeatherDataControllerIT {

    @LocalServerPort
    private int port;

    final TestRestTemplate restTemplate = new TestRestTemplate();

    final HttpHeaders headers = new HttpHeaders();

    @Test
    @Order(1)
    public void testGetWeatherDataForAllSupportedCities() {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<List<WeatherData>> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/weather/cities",
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                }
        );
        List<WeatherData> weatherDataList = response.getBody();

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
    public void testGetWeatherDataForSelectedCities() {

        String cities = "tallinn,tartu,pärnu";
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<WeatherData>> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/weather/cities/" + cities,
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        List<WeatherData> weatherDataList = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        assertNotNull(weatherDataList);
        assertEquals(3, weatherDataList.size());

        WeatherData tallinnWeatherData = weatherDataList.get(0);
        WeatherData tartuWeatherData = weatherDataList.get(1);
        WeatherData parnuWeatherData = weatherDataList.get(2);

        assertEquals(tallinnWeatherData.getStationName(), "tallinn");
        assertEquals(tallinnWeatherData.getWmoCode(), 26038L);
        assertEquals(tartuWeatherData.getStationName(), "tartu");
        assertEquals(tartuWeatherData.getWmoCode(), 26242L);
        assertEquals(parnuWeatherData.getStationName(), "pärnu");
        assertEquals(parnuWeatherData.getWmoCode(), 41803L);

        for (WeatherData weatherData : weatherDataList) {
            assertNotNull(weatherData.getId());
            assertNotNull(weatherData.getAirTemperature());
            assertNotNull(weatherData.getWindSpeed());
            assertNotNull(weatherData.getWeatherPhenomenon());
            assertNotNull(weatherData.getRest_timestamp());
        }

    }

    @Test
    @Order(3)
    public void testGetAllWeatherData() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<WeatherData>> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/weather",
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        List<WeatherData> weatherDataList = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        assertNotNull(weatherDataList);
        assertTrue(weatherDataList.size() > 0);

        for (WeatherData weatherData : weatherDataList) {
            assertNotNull(weatherData.getId());
            assertNotNull(weatherData.getStationName());
            assertNotNull(weatherData.getWmoCode());
            assertNotNull(weatherData.getAirTemperature());
            assertNotNull(weatherData.getWindSpeed());
            assertNotNull(weatherData.getWeatherPhenomenon());
            assertNotNull(weatherData.getRest_timestamp());
        }

    }

    @Test
    //@BeforeTestMethod
    @Order(4)
    public void testAddWeatherData() throws InterruptedException, IOException {
        String stationName = "tallinn";
        Long wmoCode = 26038L;
        Double airTemperature = 1.3;
        Double windSpeed = 25.0;
        String weatherPhenomenon = "Heavy rain";



        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<WeatherData> response = restTemplate.exchange(
                "http://localhost:" + port +
                        String.format("/api/weather" +
                                "?stationName=%s&wmoCode=%s&airTemperature=%s&windSpeed=%s&weatherPhenomenon=%s",
                                stationName, wmoCode, airTemperature, windSpeed, weatherPhenomenon),

                HttpMethod.POST, entity, new ParameterizedTypeReference<>() {}
        );
        WeatherData weatherDataResponse = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(weatherDataResponse);

        assertNotNull(weatherDataResponse.getId());
        assertNotNull(weatherDataResponse.getStationName());
        assertNotNull(weatherDataResponse.getWmoCode());
        assertNotNull(weatherDataResponse.getAirTemperature());
        assertNotNull(weatherDataResponse.getWindSpeed());
        //assertNotNull(weatherDataResponse.getWeatherPhenomenon()); can be null
        assertNotNull(weatherDataResponse.getRest_timestamp());

        assertEquals(weatherDataResponse.getStationName(), stationName);
        assertEquals(weatherDataResponse.getWmoCode(), wmoCode);
        assertEquals(weatherDataResponse.getAirTemperature(), airTemperature);
        assertEquals(weatherDataResponse.getWindSpeed(), windSpeed);
        assertEquals(weatherDataResponse.getWeatherPhenomenon(), weatherPhenomenon);

        // does not work with breakpoints if you wait long enough
/*        it works but if you wait long enough it will not assert
        assertEquals(
                weatherDataResponse.getRest_timestamp().truncatedTo(ChronoUnit.MINUTES),
                OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
                        .truncatedTo(ChronoUnit.MINUTES)
                        .withOffsetSameInstant(ZoneOffset.UTC)
        );*/

        TimeUnit.SECONDS.sleep(2);
        testGetWeatherDataById(weatherDataResponse.getId());
        TimeUnit.SECONDS.sleep(2);
        testPatchWeatherDataById(weatherDataResponse.getId());
        TimeUnit.SECONDS.sleep(2);
        testDeleteWeatherDataById(weatherDataResponse.getId());
    }

    public void testGetWeatherDataById(Long id) {
        String stationName = "tallinn";
        Long wmoCode = 26038L;
        Double airTemperature = 1.3;
        Double windSpeed = 25.0;
        String weatherPhenomenon = "Heavy rain";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<WeatherData> response = restTemplate.exchange(
                "http://localhost:" + port +
                        String.format("/api/weather/%S", id),
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );

        WeatherData weatherDataResponse = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        assertNotNull(weatherDataResponse);
        assertNotNull(weatherDataResponse.getId());
        assertNotNull(weatherDataResponse.getStationName());
        assertNotNull(weatherDataResponse.getWmoCode());
        assertNotNull(weatherDataResponse.getAirTemperature());
        assertNotNull(weatherDataResponse.getWindSpeed());
        //assertNotNull(weatherDataResponse.getWeatherPhenomenon()); can be null
        assertNotNull(weatherDataResponse.getRest_timestamp());

        assertEquals(weatherDataResponse.getStationName(), stationName);
        assertEquals(weatherDataResponse.getWmoCode(), wmoCode);
        assertEquals(weatherDataResponse.getAirTemperature(), airTemperature);
        assertEquals(weatherDataResponse.getWindSpeed(), windSpeed);
        assertEquals(weatherDataResponse.getWeatherPhenomenon(), weatherPhenomenon);

    }

    // see https://github.com/spring-projects/spring-framework/issues/19618
    public void testPatchWeatherDataById(Long id) throws IOException {
        String stationName = "tallinn";
        Long wmoCode = 26038L;
        Double airTemperature = -20.5;
        Double windSpeed = 3.0;
        String weatherPhenomenon = "Thunder";

        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        OkHttpClient client = new OkHttpClient();

        HttpUrl url = HttpUrl.parse("http://localhost:" + port + "/api/weather/" + id);
        assertNotNull(url);

        HttpUrl.Builder urlBuilder = url.newBuilder();
        urlBuilder.addQueryParameter("airTemperature", String.valueOf(airTemperature));
        urlBuilder.addQueryParameter("windSpeed", String.valueOf(windSpeed));
        urlBuilder.addQueryParameter("weatherPhenomenon", weatherPhenomenon);
        url = urlBuilder.build();

        //noinspection deprecation
        Request request = new Request.Builder()
                .url(url)
                .patch(RequestBody.create(null, new byte[0]))
                .build();

        Response response = client.newCall(request).execute();
        assertEquals(response.code(), HttpStatus.OK.value());

        WeatherData weatherDataResponse =
                objectMapper.readValue(Objects.requireNonNull(response.body()).bytes(), WeatherData.class);

        assertNotNull(weatherDataResponse);
        assertNotNull(weatherDataResponse.getId());
        assertNotNull(weatherDataResponse.getStationName());
        assertNotNull(weatherDataResponse.getWmoCode());
        assertNotNull(weatherDataResponse.getAirTemperature());
        assertNotNull(weatherDataResponse.getWindSpeed());
        //assertNotNull(weatherDataResponse.getWeatherPhenomenon()); can be null
        assertNotNull(weatherDataResponse.getRest_timestamp());

        assertEquals(weatherDataResponse.getStationName(), stationName);
        assertEquals(weatherDataResponse.getWmoCode(), wmoCode);
        assertEquals(weatherDataResponse.getAirTemperature(), airTemperature);
        assertEquals(weatherDataResponse.getWindSpeed(), windSpeed);
        assertEquals(weatherDataResponse.getWeatherPhenomenon(), weatherPhenomenon);
    }

    public void testDeleteWeatherDataById(Long id) {

        String expectedResponse = String.format("Weather data with id: ´%s´ was deleted", id);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/weather/" + id,
                HttpMethod.DELETE, entity, new ParameterizedTypeReference<>() {}
        );
        String weatherDataResponse = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        assertNotNull(weatherDataResponse);
        assertEquals(weatherDataResponse, expectedResponse);
    }

    @Test
    @Order(5)
    //@DependsOn("testAddWeatherData")
    public void testGetWeatherDataByIdFail() {
        Long id = null;

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<WeatherData> response = restTemplate.exchange(
                "http://localhost:" + port +
                        String.format("/api/weather/%s", id),
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );

        WeatherData weatherDataResponse = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNotNull(weatherDataResponse);
    }

    @Test
    public void testGetWeatherDataByIdFail2() {
        Long id = Long.MAX_VALUE;

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<WeatherData> response = restTemplate.exchange(
                "http://localhost:" + port +
                        String.format("/api/weather/%s", id),
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );

        WeatherData weatherDataResponse = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertNotNull(weatherDataResponse);
    }

}
package com.example.FoodDeliveryDemoApp.integration.controller;

import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.domain.WeatherData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
@AutoConfigureMockMvc
public class WeatherDataControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Value("${host.url.test}")
    private String hostUrl;

    private final String apiUrl = "/api/v1/weather";

    @Test
    @Order(1)
    public void testGetWeatherDataForAllSupportedCities() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                hostUrl + port + apiUrl + "/cities")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<WeatherData> weatherDataList = objectMapper.readValue(content, new TypeReference<>() {});

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
    public void testGetWeatherDataForSelectedCities() throws Exception {
        String cities = "tallinn,tartu,pärnu";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                hostUrl + port + apiUrl + "/cities/" + cities)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<WeatherData> weatherDataList = objectMapper.readValue(content, new TypeReference<>() {});

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
    public void testGetAllWeatherData() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        List<WeatherData> weatherDataList = objectMapper.readValue(content, new TypeReference<>() {});

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
    public void testAddWeatherData() throws Exception {
        String stationName = "tallinn";
        Long wmoCode = 26038L;
        Double airTemperature = 1.3;
        Double windSpeed = 25.0;
        String weatherPhenomenon = "Heavy rain";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                hostUrl+ port + apiUrl + "?" +
                        String.format("stationName=%s&wmoCode=%s&airTemperature=%s&windSpeed=%s&weatherPhenomenon=%s",
                                stationName, wmoCode, airTemperature, windSpeed, weatherPhenomenon))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        WeatherData weatherDataResponse = objectMapper.readValue(content, WeatherData.class);

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

    public void testGetWeatherDataById(Long id) throws Exception {
        String stationName = "tallinn";
        Long wmoCode = 26038L;
        Double airTemperature = 1.3;
        Double windSpeed = 25.0;
        String weatherPhenomenon = "Heavy rain";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        WeatherData weatherDataResponse = objectMapper.readValue(content, WeatherData.class);

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

    public void testPatchWeatherDataById(Long id) throws Exception {
        String stationName = "tallinn";
        Long wmoCode = 26038L;
        Double airTemperature = -20.5;
        Double windSpeed = 3.0;
        String weatherPhenomenon = "Thunder";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(
                                hostUrl + port + apiUrl + "/" + id)
                        .param("airTemperature", String.valueOf(airTemperature))
                        .param("windSpeed", String.valueOf(windSpeed))
                        .param("weatherPhenomenon", weatherPhenomenon)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        WeatherData weatherDataResponse = objectMapper
                .readValue(content, WeatherData.class);

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

    public void testDeleteWeatherDataById(Long id) throws Exception {
        String expectedResponse = String.format("Weather data with id: ´%s´ was deleted", id);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(
                                hostUrl + port + apiUrl + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertNotNull(content);

        assertEquals(content, expectedResponse);
    }

    @Test
    @Order(5)
    public void testGetWeatherDataByIdFail() throws Exception {
        Long id = null;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        WeatherData weatherDataResponse = objectMapper.readValue(content, WeatherData.class);
        assertNotNull(weatherDataResponse);
    }

    @Test
    public void testGetWeatherDataByIdFail2() throws Exception {
        long id = Long.MAX_VALUE;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(hostUrl + port + apiUrl + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        WeatherData weatherDataResponse = objectMapper.readValue(content, WeatherData.class);
        assertNotNull(weatherDataResponse);
    }

}
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
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Map;

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
public class ExternalWeatherDataControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Value("${host.url.test}")
    private String hostUrl;

    private final String apiUrl = "/api/v1/get-weather-from-eea";

    @Test
    @Order(1)
    void testGetAndSaveWeatherObservations() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post( hostUrl + port + apiUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
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
    public void testGetWeatherDataFromServiceXML() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get( hostUrl + port + apiUrl + "/xml")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String xmlResponse = mvcResult.getResponse().getContentAsString();

        // Validate the response is a valid XML string
        assertNotNull(xmlResponse);
    }

    @Test
    @Order(3)
    void testGetPossibleStationNamesFromServiceXML() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                hostUrl + port + apiUrl + "/stations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Map<String, Long> stationNamesAndCodes = objectMapper.readValue(content, new TypeReference<>() {});

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
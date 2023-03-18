package com.example.FoodDeliveryDemoApp.service;

import com.example.FoodDeliveryDemoApp.exception.ExternalServiceException;
import com.example.FoodDeliveryDemoApp.exception.UnauthorizedException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    @Value("${weather.data.url}")
    private String weatherObservationsUrl;

    private final WebClient webClient;

    public WeatherDataServiceImpl() {
        this.webClient = WebClient.builder()
                .defaultHeader("Accept", MediaType.APPLICATION_XML_VALUE)
                .build();
    }

    @Override
    public String retrieveWeatherObservations() {
        try {
            String userAgent = "PostmanRuntime/7.31.1";

            return webClient.get()
                    .uri(weatherObservationsUrl)
                    .header("User-Agent", userAgent)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new NotFoundException("Data not found");
            } else if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new UnauthorizedException("Unauthorized access");
            } else if (e.getStatusCode().is5xxServerError()) {
                throw new ExternalServiceException("Error in external service");
            } else {
                throw new RuntimeException("Unknown error occurred");
            }
        }
    }
}

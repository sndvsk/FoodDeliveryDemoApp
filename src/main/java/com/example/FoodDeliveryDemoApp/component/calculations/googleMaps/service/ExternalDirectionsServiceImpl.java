package com.example.FoodDeliveryDemoApp.component.calculations.googleMaps.service;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.calculations.googleMaps.dto.DirectionDTO;
import com.example.FoodDeliveryDemoApp.exception.ExternalServiceException;
import com.example.FoodDeliveryDemoApp.exception.UnauthorizedException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Instant;
import java.util.List;

@Component
public class ExternalDirectionsServiceImpl implements ExternalDirectionsService {

    @Value("${google.directions.api.key}")
    private String googleDirectionsApiKey;

    private final WebClient webClient;

    public ExternalDirectionsServiceImpl() {
        this.webClient = WebClient.builder()
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Retrieves directions from Google Directions API.
     *
     * @param addressDTOS the starting point for the directions and the ending point for the directions.
     * @return a DirectionDataDTO object representing the directions from origin to destination.
     * @throws NotFoundException if the requested data is not found.
     * @throws UnauthorizedException if the request is unauthorized.
     * @throws ExternalServiceException if there is an error in the external service.
     * @throws RuntimeException if an unknown error occurs.
     */
    @Override
    public DirectionDTO getDirections(List<AddressDTO> addressDTOS) {

        AddressDTO originAddress = addressDTOS.get(0);
        AddressDTO destinationAddress = addressDTOS.get(1);

        String origin = String.join(", ",
                originAddress.getStreet(),
                originAddress.getHouseNumber(),
                originAddress.getCity(),
                originAddress.getCountry(),
                String.valueOf(originAddress.getZipCode()));

        String destination = String.join(", ",
                destinationAddress.getStreet(),
                destinationAddress.getHouseNumber(),
                destinationAddress.getCity(),
                destinationAddress.getCountry(),
                String.valueOf(destinationAddress.getZipCode()));


        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("maps.googleapis.com")
                            .path("/maps/api/directions/json")
                            .queryParam("origin", origin)
                            .queryParam("destination", destination)
                            .queryParam("departure_time", Instant.now())
                            .queryParam("key", googleDirectionsApiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(DirectionDTO.class)
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


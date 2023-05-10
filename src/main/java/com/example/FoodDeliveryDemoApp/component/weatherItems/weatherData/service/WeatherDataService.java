package com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.service;

import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.domain.WeatherData;
import jakarta.xml.bind.JAXBException;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

public interface WeatherDataService {

    WeatherData getLastDataByCity(String city, OffsetDateTime dateTime) throws CustomNotFoundException;

    List<WeatherData> getAllWeatherData();

    WeatherData addWeatherData(
            String stationName, Long wmoCode, Double airTemperature,
            Double windSpeed, String weatherPhenomenon, OffsetDateTime dateTime) throws JAXBException;

    WeatherData getWeatherDataById(Long weatherId);

    WeatherData patchWeatherDataById(Long weatherId, Double airTemperature, Double windSpeed, String weatherPhenomenon);

    String deleteWeatherDataById(Long weatherId);

    List<WeatherData> getLastWeatherDataForAllCities(String cities);

    List<WeatherData> getWeatherDataFromExternalService() throws JAXBException;

    List<WeatherData> getAndSaveWeatherDataFromExternalService() throws JAXBException;

    Instant getLastSaveTimestamp();

}

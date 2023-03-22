package com.example.FoodDeliveryDemoApp.service.WeatherData;

import com.example.FoodDeliveryDemoApp.dto.WeatherDataDTO;
import com.example.FoodDeliveryDemoApp.exception.weatherdata.WeatherDataBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.weatherdata.WeatherDataNotFoundException;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import jakarta.xml.bind.JAXBException;

import java.time.OffsetDateTime;
import java.util.List;

public interface WeatherDataService {

    List<WeatherDataDTO.Station> filterResponse(String response) throws JAXBException;

    List<WeatherData> convertStationsToWeatherData(List<WeatherDataDTO.Station> stations);

    List<WeatherData> getLastWeatherDataForAllCities();

    void saveWeatherData(List<WeatherData> weatherDataList);

    void validateCities(String cities) throws WeatherDataBadRequestException;

    WeatherData validateWeatherDataInputs(WeatherData weatherData, Double airTemperature, Double windSpeed, String weatherPhenomenon);

    List<WeatherData> getWeatherDataFromService() throws JAXBException;

    WeatherData getLastDataByCity(String city);

    WeatherData getLastDataByCity(String city, OffsetDateTime dateTime) throws WeatherDataNotFoundException;

    List<WeatherData> getWeatherDataFromRepository(String cities);

    List<WeatherData> getAndSaveWeatherDataFromService() throws JAXBException;

    WeatherData getWeatherDataById(Long weatherId);

    String deleteWeatherDataById(Long weatherId);

    WeatherData patchWeatherDataById(Long weatherId, Double airTemperature, Double windSpeed, String weatherPhenomenon);
}

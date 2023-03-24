package com.example.FoodDeliveryDemoApp.service.weatherData;

import com.example.FoodDeliveryDemoApp.dto.WeatherDataDTO;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import jakarta.xml.bind.JAXBException;

import java.time.OffsetDateTime;
import java.util.List;

public interface WeatherDataService {

    List<WeatherData> getLastWeatherDataForAllCities();

    WeatherData getLastDataByCity(String city, OffsetDateTime dateTime) throws CustomNotFoundException;

    List<WeatherData> getAllWeatherData();

    WeatherData addWeatherData(String stationName, Long wmoCode, Double airTemperature, Double windSpeed, String weatherPhenomenon, OffsetDateTime dateTime) throws JAXBException;

    WeatherData getWeatherDataById(Long weatherId);

    WeatherData patchWeatherDataById(Long weatherId, Double airTemperature, Double windSpeed, String weatherPhenomenon);

    String deleteWeatherDataById(Long weatherId);

    List<WeatherData> getWeatherDataFromRepository(String cities);

    void saveWeatherData(List<WeatherData> weatherDataList);

    List<WeatherDataDTO.Station> filterResponse(String response) throws JAXBException;

    List<WeatherData> convertStationsToWeatherData(List<WeatherDataDTO.Station> stations);

    List<WeatherData> getWeatherDataFromExternalService() throws JAXBException;

    List<WeatherData> getAndSaveWeatherDataFromExternalService() throws JAXBException;

}

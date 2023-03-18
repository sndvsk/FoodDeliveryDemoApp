package com.example.FoodDeliveryDemoApp.component;

import com.example.FoodDeliveryDemoApp.dto.WeatherDataDTO;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.repository.WeatherDataRepository;
import com.example.FoodDeliveryDemoApp.service.WeatherDataService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WeatherDataComponent {

    private final WeatherDataRepository weatherDataRepository;
    private final WeatherDataService weatherDataService;

    private final List<String> neededStations = Arrays.asList("26038", "26242", "41803");

    public WeatherDataComponent(WeatherDataRepository weatherDataRepository, WeatherDataService weatherDataService) {
        this.weatherDataRepository = weatherDataRepository;
        this.weatherDataService = weatherDataService;
    }

    @SuppressWarnings("unused")
    public void deleteAllData() {
        weatherDataRepository.deleteAll();
    }

    public void saveWeatherData(List<WeatherData> weatherDataList) {
        for (WeatherData weatherData: weatherDataList) {
            weatherDataRepository.save(weatherData);
        }
    }

    public List<WeatherDataDTO.Station> filterResponse(String response) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(WeatherDataDTO.Observations.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(response);
        WeatherDataDTO.Observations observations = (WeatherDataDTO.Observations) unmarshaller.unmarshal(reader);

        Instant timestamp = Instant.ofEpochSecond(observations.getTimestamp()); // get timestamp from XML

        return observations.getStations().stream()
                .filter(station -> neededStations.contains(station.getWmocode()))
                .peek(station -> station.setTimestamp(timestamp))
                .collect(Collectors.toList());
    }

    public List<WeatherData> convertStationsToWeatherData(List<WeatherDataDTO.Station> stations) {
        Map<String, String> nameMappings = new HashMap<>();
        nameMappings.put("Tallinn-Harku", "Tallinn");
        nameMappings.put("Tartu-Tõravere", "Tartu");

        List<WeatherData> weatherDataList = new ArrayList<>();
        for (WeatherDataDTO.Station station : stations) {
            String stationName = nameMappings.getOrDefault(station.getName(), station.getName());

            WeatherData weatherData = new WeatherData();
            weatherData.setStationName(stationName);
            weatherData.setWmoCode(station.getWmocode());
            weatherData.setAirTemperature(station.getAirtemperature());
            weatherData.setWindSpeed(station.getWindspeed());
            weatherData.setWeatherPhenomenon(station.getPhenomenon());
            weatherData.setTimestamp(station.getTimestamp());
            weatherDataList.add(weatherData);
        }
        return weatherDataList;
    }

    public List<WeatherData> getLastWeatherDataForAllCities() {
        WeatherData lastEntry = weatherDataRepository.findTopByOrderByIdDesc();
        Instant timestamp = lastEntry.getTimestamp();
        return weatherDataRepository.findByTimestamp(timestamp);
    }

    public WeatherData getLastDataByCity(String city) {
        return weatherDataRepository.findFirstByStationNameOrderByTimestampDesc(city);
    }

    public List<WeatherData> getWeatherDataFromRepository(String cities) {
        List<WeatherData> lastWeatherData = getLastWeatherDataForAllCities();

        if (cities != null) {
            lastWeatherData = lastWeatherData.stream()
                    .filter(weatherData -> cities.contains(weatherData.getStationName()))
                    .toList();
        }

        return lastWeatherData;
    }


    public List<WeatherData> getWeatherDataFromService() throws JAXBException {
        String response = weatherDataService.retrieveWeatherObservations();
        return convertStationsToWeatherData(filterResponse(response));
    }

    public List<WeatherData> getWeatherDataFromServiceAndSave() throws JAXBException {
        List<WeatherData> weatherData = getWeatherDataFromService();
        saveWeatherData(weatherData);
        return weatherData;
    }
}

package com.example.FoodDeliveryDemoApp.service.WeatherData;

import com.example.FoodDeliveryDemoApp.dto.WeatherDataDTO;
import com.example.FoodDeliveryDemoApp.exception.weatherdata.WeatherDataBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.weatherdata.WeatherDataNotFoundException;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.model.rules.ExtraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.repository.WeatherDataRepository;
import com.example.FoodDeliveryDemoApp.service.ExternalWeatherData.ExternalWeatherDataService;
import com.example.FoodDeliveryDemoApp.service.FeeRule.FeeRuleService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WeatherDataServiceImpl implements WeatherDataService {

    private WeatherDataRepository weatherDataRepository;
    private ExternalWeatherDataService externalWeatherDataService;
    private FeeRuleService feeRuleService;


    private final List<String> neededStationsNames = Arrays.asList("tallinn", "tartu", "pärnu");
    private final List<String> neededStationsWmo = Arrays.asList("26038", "26242", "41803");

    public WeatherDataServiceImpl(WeatherDataRepository weatherDataRepository, ExternalWeatherDataService externalWeatherDataService, FeeRuleService feeRuleService) {
        this.weatherDataRepository = weatherDataRepository;
        this.externalWeatherDataService = externalWeatherDataService;
        this.feeRuleService = feeRuleService;
    }

    public WeatherDataServiceImpl() {
    }

    /**
     * Filters the XML response to only include the data for the cities we need.
     *
     * @param response the XML response from external weather API
     * @return a list of DTO Station objects containing the weather data for the needed cities
     * @throws JAXBException if there is an error unmarshalling the XML response
     */
    public List<WeatherDataDTO.Station> filterResponse(String response) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(WeatherDataDTO.Observations.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(response);
        WeatherDataDTO.Observations observations = (WeatherDataDTO.Observations) unmarshaller.unmarshal(reader);

        // get timestamp from XML
        Instant timestamp = Instant.ofEpochSecond(observations.getTimestamp());

        return observations.getStations().stream()
                .filter(station -> neededStationsWmo.contains(station.getWmocode()))
                .peek(station -> station.setTimestamp(timestamp))
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of DTO Station objects to a list of WeatherData objects.
     *
     * @param stations a list of DTO Station objects containing weather data
     * @return a list of WeatherData objects created from the Station objects
     */
    public List<WeatherData> convertStationsToWeatherData(List<WeatherDataDTO.Station> stations) {
        Map<String, String> nameMappings = new HashMap<>();
        nameMappings.put("Tallinn-Harku", "tallinn");
        nameMappings.put("Tartu-Tõravere", "tartu");
        nameMappings.put("Pärnu", "pärnu");

        return stations.stream()
                .map(station -> {
                    String stationName = nameMappings.getOrDefault(station.getName(), station.getName());

                    WeatherData weatherData = new WeatherData();
                    weatherData.setStationName(stationName);
                    weatherData.setWmoCode(station.getWmocode());
                    weatherData.setAirTemperature(station.getAirtemperature());
                    weatherData.setWindSpeed(station.getWindspeed());
                    weatherData.setWeatherPhenomenon(station.getPhenomenon());
                    weatherData.setTimestamp(station.getTimestamp()
                    );

                    return weatherData;
                })
                .collect(Collectors.toList());
    }

    /**
     * Gets the last weather data entry for all cities from the repository.
     *
     * @return a list of WeatherData objects representing the last data entry for all cities
     */
    public List<WeatherData> getLastWeatherDataForAllCities() {
        Instant lastEntryTimestamp = weatherDataRepository.findTopByOrderByIdDesc().getTimestamp();
        return weatherDataRepository.findByTimestamp(lastEntryTimestamp);
    }

    /**
     * Saves a list of weather data to the weather data repository.
     *
     * @param weatherDataList the list of weather data to be saved
     */
    public void saveWeatherData(List<WeatherData> weatherDataList) {
        for (WeatherData weatherData: weatherDataList) {
            weatherDataRepository.save(weatherData);
        }
    }

    /**
     * Validates that the given string of cities are valid and exist in the list of needed station names.
     *
     * @param cities a string containing comma-separated city names
     * @throws WeatherDataBadRequestException if any of the city names are invalid
     */
    public void validateCities(String cities) throws WeatherDataBadRequestException {
        String[] cityArray = cities.split(",");
        for (String city : cityArray) {
            if (!neededStationsNames.contains(city.trim().toLowerCase())) {
                throw new WeatherDataBadRequestException(String.format("Invalid city name: ´%s´", city.trim()));
            }
        }
    }

    public WeatherData validateWeatherDataInputs(WeatherData weatherData, Double airTemperature, Double windSpeed, String weatherPhenomenon) {
        if (airTemperature != null) {
            if (airTemperature < -273.15) {
                throw new WeatherDataBadRequestException(String.format("Provided air temperature: ´%s´ is lower than absolute zero.", airTemperature));
            }
            weatherData.airTemperature = airTemperature;
        }
        if (windSpeed != null) {
            if (windSpeed < 0.0) {
                throw new WeatherDataBadRequestException(String.format("Provided wind speed: ´%s´ is lower than zero.", windSpeed));
            }
            weatherData.windSpeed = windSpeed;
        }
        if (weatherPhenomenon != null) {
            ExtraFeeWeatherPhenomenonRule weatherPhenomenonFromRepository = feeRuleService.findByWeatherPhenomenonName(weatherPhenomenon);
            weatherData.weatherPhenomenon = weatherPhenomenonFromRepository.getWeatherPhenomenonName();
        }
        return weatherData;
    }

    /**
     * Retrieves weather data from the external weather API and returns it as a list of WeatherData objects.
     *
     * @return a list of WeatherData objects containing the weather data for the needed cities
     * @throws JAXBException if there is an error unmarshalling the XML response from the weather API
     */
    public List<WeatherData> getWeatherDataFromService() throws JAXBException {
        String response = externalWeatherDataService.retrieveWeatherObservations();
        return convertStationsToWeatherData(filterResponse(response));
    }

    /**
     * Overload of the {@link #getLastDataByCity(String, OffsetDateTime)} method with a default {@code dateTime}.
     */
    public WeatherData getLastDataByCity(String city) {
        return getLastDataByCity(city, null);
    }

    /**
     * Retrieves the last weather data available for a given city and datetime.
     * If the datetime is null, it retrieves the latest data available for that city.
     *
     * @param city the name of the city for which to retrieve the weather data
     * @param dateTime the date-time for which to retrieve the weather data. If null, retrieves the latest data
     * @return the last weather data for the given city and date-time
     * @throws WeatherDataNotFoundException if no weather data is found for the given city and datetime
     */
    public WeatherData getLastDataByCity(String city, OffsetDateTime dateTime) throws WeatherDataNotFoundException {
        if (dateTime == null) {
            return weatherDataRepository.findFirstByStationNameOrderByTimestampDesc(city.toLowerCase(Locale.ROOT));
        } else {
            // todo
            // find entry in database that is +- 1 hour of the datetime
            Instant dt = dateTime.truncatedTo(ChronoUnit.SECONDS).toInstant();
            Optional<WeatherData> weatherData = weatherDataRepository.findByStationNameAndTimestamp(city.toLowerCase(Locale.ROOT), dt);

            return weatherData.
                    orElseThrow(() -> new WeatherDataNotFoundException(String.format("Weather data for this datetime: ´%s´ does not exist", dateTime)));
        }
    }

    /**
     * Retrieves the latest weather data for all cities or a specific set of cities from the weather data repository.
     * If a specific set of cities is provided, only the weather data for those cities will be returned.
     *
     * @param cities a string representing a comma-separated list of cities to retrieve weather data for (optional)
     * @return a list of WeatherData objects representing the latest weather data for all or a specific set of cities
     * @throws WeatherDataBadRequestException if any of the city names provided are invalid
     */
    public List<WeatherData> getWeatherDataFromRepository(String cities) {
        List<WeatherData> lastWeatherData = getLastWeatherDataForAllCities();

        if (cities != null) {
            String finalCities = cities.toLowerCase(Locale.ROOT);
            validateCities(finalCities);
            lastWeatherData = lastWeatherData.stream()
                    .filter(weatherData -> finalCities.contains(weatherData.getStationName()))
                    .toList();
        }

        return lastWeatherData;
    }

    /**
     * Retrieves weather data from the external weather service and saves it to the database.
     *
     * @return a list of WeatherData objects representing the weather data retrieved from the service.
     * @throws JAXBException if there is an error in the unmarshalling of the XML response from the service.
     */
    public List<WeatherData> getAndSaveWeatherDataFromService() throws JAXBException {
        List<WeatherData> weatherData = getWeatherDataFromService();
        saveWeatherData(weatherData);
        return weatherData;
    }

    public WeatherData getWeatherDataById(Long weatherId) throws WeatherDataNotFoundException {
        Optional<WeatherData> weatherData = weatherDataRepository.findById(weatherId);

        return weatherData.
                orElseThrow(() -> new WeatherDataNotFoundException(String.format("Weather data for this id: ´%s´ does not exist", weatherId)));

    }

    public String deleteWeatherDataById(Long weatherId) {
        if (weatherDataRepository.existsById(weatherId)) {
            weatherDataRepository.deleteById(weatherId);
            return String.format("Weather data with id: ´%s´ was deleted", weatherId);
        } else
            throw new WeatherDataNotFoundException(String.format("Weather data for this id: ´%s´ does not exist", weatherId));
    }

    public WeatherData patchWeatherDataById(Long weatherId, Double airTemperature, Double windSpeed, String weatherPhenomenon) {
        Optional<WeatherData> weatherData = weatherDataRepository.findById(weatherId);

        WeatherData patchedWeatherData = weatherData
                .orElseThrow(() -> new WeatherDataNotFoundException(String.format("Weather data for this id: ´%s´ does not exist", weatherId)));

        patchedWeatherData = validateWeatherDataInputs(patchedWeatherData, airTemperature, windSpeed, weatherPhenomenon);
        return weatherDataRepository.save(patchedWeatherData);
    }

}

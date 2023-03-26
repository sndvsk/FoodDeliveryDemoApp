package com.example.FoodDeliveryDemoApp.service.weatherData;

import com.example.FoodDeliveryDemoApp.dto.WeatherDataDTO;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.model.rules.extraFee.ExtraFeeWeatherPhenomenonRule;
import com.example.FoodDeliveryDemoApp.repository.WeatherDataRepository;
import com.example.FoodDeliveryDemoApp.service.externalWeatherData.ExternalWeatherDataService;
import com.example.FoodDeliveryDemoApp.service.feeRule.extraFee.weatherPhenomenonRule.ExtraFeeWeatherPhenomenonRuleService;
import com.example.FoodDeliveryDemoApp.service.feeRule.regionalBaseFee.RegionalBaseFeeRuleService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WeatherDataServiceImpl implements WeatherDataService {

    private final WeatherDataRepository weatherDataRepository;
    private final ExternalWeatherDataService externalWeatherDataService;
    private final ExtraFeeWeatherPhenomenonRuleService weatherPhenomenonRuleService;
    private final RegionalBaseFeeRuleService baseFeeRuleService;

    public WeatherDataServiceImpl(WeatherDataRepository weatherDataRepository,
                                  ExternalWeatherDataService externalWeatherDataService,
                                  ExtraFeeWeatherPhenomenonRuleService weatherPhenomenonRuleService,
                                  RegionalBaseFeeRuleService baseFeeRuleService) {
        this.weatherDataRepository = weatherDataRepository;
        this.externalWeatherDataService = externalWeatherDataService;
        this.weatherPhenomenonRuleService = weatherPhenomenonRuleService;
        this.baseFeeRuleService = baseFeeRuleService;
    }

    /**
     * Returns a TreeMap of fixed naming values retrieved from the external weather data service.
     *
     * @return a TreeMap of fixed naming values
     */
    private TreeMap<String, String> getFixedNaming() {
        return externalWeatherDataService.fixedNaming();
    }

    /**
     * Returns a TreeMap of possible station names and codes retrieved from the external weather data service using fixed naming.
     *
     * @return a TreeMap of station names and codes
     * @throws JAXBException if an error occurs while retrieving the data
     */
    private TreeMap<String, Long> getStationNamesAndCodes() throws JAXBException {
        return externalWeatherDataService.getPossibleStationNamesAndCodesFixedNaming();
    }

    /**
     * Returns a Set of all unique city names retrieved from the base fee rule service.
     *
     * @return a Set of all unique city names
     */
    private Set<String> getStationNamesFromRepository() {
        return baseFeeRuleService.getAllUniqueCities();
    }

    /**
     * Validates that the given string of cities are valid and exist in the list of needed station names.
     *
     * @param cities a string containing comma-separated city names
     * @throws CustomBadRequestException if any of the city names are invalid
     */
    private void validateCities(String cities) throws CustomBadRequestException {

        Set<String> neededStationsNames = getStationNamesFromRepository();
        String[] cityArray = cities.split(",");
        for (String city : cityArray) {
            if (!neededStationsNames.contains(city.toLowerCase())) {
                throw new CustomBadRequestException(String.format("Invalid city name: ´%s´", city.trim()));
            }
        }
    }

    /**
     * Validates the required inputs for weather data.
     *
     * @param stationName the name of the station where the weather data was collected
     * @param airTemperature the air temperature at the station in degrees Celsius
     * @param windSpeed the wind speed at the station in kilometers per hour
     * @throws CustomBadRequestException if any of the required inputs are missing or invalid
     * @throws CustomBadRequestException if the station is not supported by application
     */
    private void validateRequiredInputs(String stationName, Double airTemperature, Double windSpeed) throws CustomBadRequestException {

        if (stationName == null) {
            throw new CustomBadRequestException("Station name is not provided");
        }

        Set<String> possibleCities = getStationNamesFromRepository();

        if (!possibleCities.contains(stationName.toLowerCase())) {
            throw new CustomBadRequestException(String.format("Station name: '%s' is not yet supported by this application", stationName));
        }

        if (airTemperature == null) {
            throw new CustomBadRequestException("Air temperature is not provided");
        }
        if (windSpeed == null) {
            throw new CustomBadRequestException("Wind speed is not provided");
        }
    }

    /**
     * Validates the input air temperature and wind speed to ensure they are within valid ranges.
     *
     * @param airTemperature the air temperature to be validated
     * @param windSpeed the wind speed to be validated
     * @throws CustomBadRequestException if either airTemperature or windSpeed is outside of the valid range
     */
    private void validateInputs(Double airTemperature, Double windSpeed) throws CustomBadRequestException {
        if (airTemperature < -273.15) {
            throw new CustomBadRequestException(String.format("Provided air temperature: ´%s´ is lower than absolute zero.", airTemperature));
        } else if (airTemperature > 100.0) {
            throw new CustomBadRequestException(String.format("Provided air temperature: ´%s´ is too high for Earth surface.", airTemperature));
        }
        if (windSpeed < 0.0) {
            throw new CustomBadRequestException(String.format("Provided wind speed: ´%s´ is lower than zero.", windSpeed));
        } else if (windSpeed > 150.0) {
            throw new CustomBadRequestException(String.format("Provided wind speed: ´%s´ is too high for Earth surface.", windSpeed));
        }

    }

    /**
     * Validates the provided wmo code is correct for the provided station name.
     *
     * @param stationName the name of the weather station
     * @param wmoCode the wmo code to be validated
     * @return the wmo code if it is correct for the provided station name, or null otherwise
     * @throws JAXBException if there is an error getting the station names and codes
     * @throws CustomBadRequestException if the provided wmo code is not correct for the provided stationName
     */
    private Long validateWmoCode(String stationName, Long wmoCode) throws JAXBException, CustomBadRequestException {

        TreeMap<String, Long> possibleCities = getStationNamesAndCodes();

        Long expectedWmoCode = possibleCities.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(stationName))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);

        if (wmoCode == null || Objects.equals(expectedWmoCode, wmoCode)) {
            return expectedWmoCode;
        } else {
            throw new CustomBadRequestException(String.format("Provided wmo code: ´%s´ is not the wmo code for ´%s´", wmoCode, stationName));
        }
    }

    /**
     * Finds the closest WeatherData object to the provided city, date, and time, and returns it.
     *
     * @param city the name of the city
     * @param dt the date and time to search around
     * @param dateTime the full date and time object, used for error messages
     * @return the closest WeatherData object to the provided city, date, and time
     * @throws CustomNotFoundException if there is no WeatherData object for or near the provided datetime
     * @throws CustomBadRequestException if the closest WeatherData object to the provided datetime is too far away
     */
    private WeatherData getClosestWeatherDataFromRepository(String city, Instant dt, OffsetDateTime dateTime) throws CustomNotFoundException, CustomBadRequestException {
        Optional<WeatherData> previousWeatherData = weatherDataRepository.findPreviousWeatherData(city, dt, PageRequest.of(0, 1)).stream().findFirst();
        Optional<WeatherData> nextWeatherData = weatherDataRepository.findNextWeatherData(city, dt, PageRequest.of(0, 1)).stream().findFirst();
        if (previousWeatherData.isEmpty() && nextWeatherData.isEmpty()) {
            throw new CustomNotFoundException(String.format("Weather data for or near this datetime: ´%s´ does not exist", dateTime));
        }

        if (previousWeatherData.isEmpty()) {
            return nextWeatherData.get();
        } else if (nextWeatherData.isEmpty()) {
            return previousWeatherData.get();
        } else {
            Instant previousTimestamp = previousWeatherData.get().getTimestamp();
            Instant nextTimestamp = nextWeatherData.get().getTimestamp();

            if (isDurationTooFar(Duration.between(previousTimestamp, dt))) {
                throw new CustomBadRequestException(String.format("Weather data with closest datetime to given datetime is too far: %s", Duration.between(previousTimestamp, dt)));
            }
            if (isDurationTooFar(Duration.between(nextTimestamp, dt))) {
                throw new CustomBadRequestException(String.format("Weather data with closest datetime to given datetime is too far: %s", Duration.between(nextTimestamp, dt)));
            }
            return Duration.between(previousTimestamp, dt).compareTo(Duration.between(nextTimestamp, dt)) <= 0
                    ? previousWeatherData.get()
                    : nextWeatherData.get();
        }
    }

    /**
     * Determines whether a duration is greater than one hour.
     *
     * @param duration the duration to check
     * @return true if the duration is greater than one hour, false otherwise
     */
    private boolean isDurationTooFar(Duration duration) {
        return duration.toHours() > 1;
    }

    /**
     * Validates that a WeatherData object for the provided stationName and dateTime does not already exist.
     *
     * @param stationName the name of the weather station
     * @param dateTime the date and time to check for an existing WeatherData object
     * @throws CustomBadRequestException if a WeatherData object for the provided stationName and datetime already exists
     */
    private void validateDateTime(String stationName, OffsetDateTime dateTime) throws CustomBadRequestException {
        Optional<WeatherData> weatherData = weatherDataRepository.findByStationNameAndTimestamp(stationName, dateTime.toInstant().truncatedTo(ChronoUnit.SECONDS));
        if (weatherData.isPresent()) {
            throw new CustomBadRequestException(String.format("There is already entry for this station: ´%s´ and datetime: ´%s´", stationName, dateTime));
        }
    }

    /**
     * Retrieves all WeatherData objects in the database.
     *
     * @return a list of all WeatherData objects in the database
     * @throws CustomNotFoundException if there are no WeatherData objects in the database
     */
    public List<WeatherData> getAllWeatherData() throws CustomNotFoundException {
        List<WeatherData> weatherDataList = weatherDataRepository.findAll();

        if (weatherDataList.isEmpty()) {
            throw new CustomNotFoundException("No weather data in the database.");
        } else {
            return weatherDataList;
        }
    }

    /**
     * Adds a new WeatherData object to the database.
     *
     * @param stationName the name of the weather station
     * @param wmoCode the wmo code for the weather station
     * @param airTemperature the air temperature for the weather observation
     * @param windSpeed the wind speed for the weather observation
     * @param weatherPhenomenon the weather phenomenon for the weather observation
     * @param dateTime (optional) the date and time for the weather observation
     * @return the newly created WeatherData object
     * @throws JAXBException if there is an error getting the station names and codes
     * @throws CustomBadRequestException if any of the inputs are invalid or the wmo code is incorrect for the provided station name
     */
    public WeatherData addWeatherData(String stationName, Long wmoCode, Double airTemperature, Double windSpeed, String weatherPhenomenon, OffsetDateTime dateTime) throws JAXBException {

        validateRequiredInputs(stationName, airTemperature, windSpeed);
        validateInputs(airTemperature, windSpeed);

        stationName = stationName.toLowerCase(Locale.ROOT);
        Long wmo = validateWmoCode(stationName, wmoCode);

        WeatherData weatherData = new WeatherData();

        weatherData.setStationName(stationName);
        weatherData.setWmoCode(wmo);
        weatherData.setAirTemperature(airTemperature);
        weatherData.setWindSpeed(windSpeed);

        if (dateTime != null) {
            validateDateTime(stationName, dateTime);
            weatherData.setTimestamp(dateTime.toInstant());
        } else {
            weatherData.setTimestamp(Instant.now());
        }

        if (weatherPhenomenon != null) {
            ExtraFeeWeatherPhenomenonRule weatherPhenomenonFromRepository = weatherPhenomenonRuleService.getByWeatherPhenomenonName(weatherPhenomenon);
            String phenomenonName = weatherPhenomenonFromRepository.getWeatherPhenomenonName();
            weatherData.setWeatherPhenomenon(phenomenonName);
        }

        return weatherDataRepository.save(weatherData);
    }

    /**
     * Retrieves a WeatherData object by its ID from the database.
     *
     * @param weatherId the ID of the weather data to retrieve
     * @return the WeatherData object with the specified ID
     * @throws CustomNotFoundException if the specified ID does not correspond to any weather data in the database
     */
    public WeatherData getWeatherDataById(Long weatherId) throws CustomNotFoundException {
        Optional<WeatherData> weatherData = weatherDataRepository.findById(weatherId);

        return weatherData.
                orElseThrow(() -> new CustomNotFoundException(String.format("Weather data for this id: ´%s´ does not exist", weatherId)));

    }

    /**
     * Updates the air temperature, wind speed, and/or weather phenomenon of a WeatherData object with the specified id
     * in the database.
     *
     * @param weatherId the ID of the weather data to update
     * @param airTemperature the new air temperature (null if it should not be updated)
     * @param windSpeed the new wind speed (null if it should not be updated)
     * @param weatherPhenomenon the new weather phenomenon (null if it should not be updated)
     * @return the updated WeatherData object
     * @throws CustomNotFoundException if the specified ID does not correspond to any weather data in the database
     * @throws CustomBadRequestException if the air temperature and/or wind speed are not provided
     */

    public WeatherData patchWeatherDataById(Long weatherId, Double airTemperature, Double windSpeed, String weatherPhenomenon) throws CustomNotFoundException {
        Optional<WeatherData> weatherData = weatherDataRepository.findById(weatherId);

        WeatherData patchedWeatherData = weatherData
                .orElseThrow(() -> new CustomNotFoundException(String.format("Weather data for this id: ´%s´ does not exist", weatherId)));

        validateInputs(airTemperature, windSpeed);

        if (airTemperature != null) {
            patchedWeatherData.airTemperature = airTemperature;
        }

        if (windSpeed != null) {
            patchedWeatherData.windSpeed = windSpeed;
        }

        if (weatherPhenomenon != null) {
            ExtraFeeWeatherPhenomenonRule weatherPhenomenonFromRepository = weatherPhenomenonRuleService.getByWeatherPhenomenonName(weatherPhenomenon);
            patchedWeatherData.weatherPhenomenon = weatherPhenomenonFromRepository.getWeatherPhenomenonName();
        }

        return weatherDataRepository.save(patchedWeatherData);
    }

    /**
     * Deletes a WeatherData object with the specified ID from the database.
     *
     * @param weatherId the ID of the weather data to delete
     * @return a message indicating that the weather data was deleted
     * @throws CustomNotFoundException if the specified ID does not correspond to any weather data in the database
     */
    public String deleteWeatherDataById(Long weatherId) throws CustomNotFoundException {
        if (weatherDataRepository.existsById(weatherId)) {
            weatherDataRepository.deleteById(weatherId);
            return String.format("Weather data with id: ´%s´ was deleted", weatherId);
        } else
            throw new CustomNotFoundException(String.format("Weather data for this id: ´%s´ does not exist", weatherId));
    }

    /**
     * Filters the XML response to only include the data for the cities we need.
     *
     * @param response the XML response from external weather API
     * @return a list of DTO Station objects containing the weather data for the needed cities
     * @throws JAXBException if there is an error unmarshalling the XML response
     */
    private List<WeatherDataDTO.Station> filterResponse(String response) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(WeatherDataDTO.Observations.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(response);
        WeatherDataDTO.Observations observations = (WeatherDataDTO.Observations) unmarshaller.unmarshal(reader);

        // get timestamp from XML
        Instant timestamp = Instant.ofEpochSecond(observations.getTimestamp());

        Set<String> neededStations = getStationNamesFromRepository();

        TreeMap<String, String> fixedNaming = getFixedNaming();

        return observations.getStations().stream()
                .filter(station -> neededStations.contains(fixedNaming.get(station.getName())))
                .peek(station -> station.setTimestamp(timestamp))
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of DTO Station objects to a list of WeatherData objects.
     *
     * @param stations a list of DTO Station objects containing weather data
     * @return a list of WeatherData objects created from the Station objects
     */
    private List<WeatherData> convertStationsToWeatherData(List<WeatherDataDTO.Station> stations) {

        TreeMap<String, String> fixedNaming = getFixedNaming();

        return stations.stream()
                .map(station -> {
                    String stationName = fixedNaming.getOrDefault(station.getName(), station.getName());

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
    private List<WeatherData> getLastWeatherDataForAllCitiesFromRepository() {
        Instant lastEntryTimestamp = weatherDataRepository.findTopByOrderByIdDesc().getTimestamp();
        return weatherDataRepository.findByTimestamp(lastEntryTimestamp);
    }

    /**
     * Saves a list of weather data to the weather data repository.
     *
     * @param weatherDataList the list of weather data to be saved
     */
    private void saveWeatherData(List<WeatherData> weatherDataList) {
        for (WeatherData weatherData: weatherDataList) {
            weatherDataRepository.save(weatherData);
        }
    }

    /**
     * Retrieves weather data from the external weather API and returns it as a list of WeatherData objects.
     *
     * @return a list of WeatherData objects containing the weather data for the needed cities
     * @throws JAXBException if there is an error unmarshalling the XML response from the weather API
     */
    public List<WeatherData> getWeatherDataFromExternalService() throws JAXBException {
        String response = externalWeatherDataService.retrieveWeatherObservations();
        List<WeatherDataDTO.Station> stationList = filterResponse(response);
        return convertStationsToWeatherData(stationList);
    }


    /**
     * Retrieves the last weather data available for a given city and datetime.
     * If the datetime is null, it retrieves the latest data available for that city.
     *
     * @param city the name of the city for which to retrieve the weather data
     * @param dateTime the date-time for which to retrieve the weather data. If null, retrieves the latest data
     * @return the last weather data for the given city and date-time
     * @throws CustomNotFoundException if no weather data is found for the given city and datetime
     */
    public WeatherData getLastDataByCity(String city, OffsetDateTime dateTime) throws CustomNotFoundException {
        if (dateTime == null) {
            return weatherDataRepository.findFirstByStationNameOrderByTimestampDesc(city);
        } else {
            Instant dt = dateTime.truncatedTo(ChronoUnit.SECONDS).toInstant();
            Optional<WeatherData> sameDtWeatherData = weatherDataRepository.findByStationNameAndTimestamp(city, dt);

            return sameDtWeatherData.orElseGet(() -> getClosestWeatherDataFromRepository(city, dt, dateTime));
        }
    }

    /**
     * Retrieves the latest weather data for all cities or a specific set of cities from the weather data repository.
     * If a specific set of cities is provided, only the weather data for those cities will be returned.
     *
     * @param cities a string representing a comma-separated list of cities to retrieve weather data for (optional)
     * @return a list of WeatherData objects representing the latest weather data for all or a specific set of cities
     */
    public List<WeatherData> getLastWeatherDataForAllCities(String cities) {
        List<WeatherData> lastWeatherData = getLastWeatherDataForAllCitiesFromRepository();

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
    public List<WeatherData> getAndSaveWeatherDataFromExternalService() throws JAXBException {
        List<WeatherData> weatherData = getWeatherDataFromExternalService();
        saveWeatherData(weatherData);
        return weatherData;
    }

}

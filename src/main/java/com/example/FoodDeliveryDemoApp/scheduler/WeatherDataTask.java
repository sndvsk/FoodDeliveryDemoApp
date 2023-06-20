package com.example.FoodDeliveryDemoApp.scheduler;

import com.example.FoodDeliveryDemoApp.component.weatherItems.weatherData.service.WeatherDataService;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.Instant;

@Component
public class WeatherDataTask {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final WeatherDataService weatherDataService;

    public WeatherDataTask(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    /**
     * Executes the method to save new weather data from external service into repository during application startup.
     *
     * @throws JAXBException if an error occurs during the JAXB processing
     */
    @PostConstruct
    private void onStartup() throws JAXBException {
        logger.info("Startup. Saving new weather data from external service into repository.");

        int minutesSinceLastInsert = 30;

        Instant lastSavedTime = Instant.now().minus(Duration.ofMinutes(
                minutesSinceLastInsert
        ));

        Instant lastSavedTimeFromService = getLastSavedTimeFromService();

        // Check if new data has been saved in the last N minutes
        if (lastSavedTimeFromService == null) {
            saveWeatherDataFromService();
            logger.info("New weather data is saved into repository.");
        } else if (lastSavedTime.isBefore(lastSavedTimeFromService)) {
            logger.info("Weather data has been saved into repository in the last {} minutes.", minutesSinceLastInsert);
        } else {
            saveWeatherDataFromService();
            logger.info("New weather data is saved into repository.");
        }
    }

    /**
     * Executes the method to save new weather data from external service into repository according to a scheduled time.
     *
     * @throws JAXBException if an error occurs during the JAXB processing
     */
    @Scheduled(cron = "${weather.data.cron-interval}")
    private void onSchedule() throws JAXBException {
        logger.info("15 minutes past current hour. Saving new weather data from external service into repository.");
        saveWeatherDataFromService();
        logger.info("New weather data is saved into repository.");
    }

    /**
     * Calls the method that retrieves and saves new weather data from external service into repository.
     *
     * @throws JAXBException if an error occurs during the JAXB processing
     */
    private void saveWeatherDataFromService() throws JAXBException {
        weatherDataService.getAndSaveWeatherDataFromExternalService();
    }

    private Instant getLastSavedTimeFromService() {
        return weatherDataService.getLastSaveTimestamp();
    }

}

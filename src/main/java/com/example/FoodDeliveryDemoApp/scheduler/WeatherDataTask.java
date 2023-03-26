package com.example.FoodDeliveryDemoApp.scheduler;

import com.example.FoodDeliveryDemoApp.service.weatherData.WeatherDataService;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataTask {

    private static final Logger logger = LoggerFactory.getLogger(WeatherDataTask.class);

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
        saveWeatherDataFromService();
        logger.info("New weather data is saved into repository.");
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

}

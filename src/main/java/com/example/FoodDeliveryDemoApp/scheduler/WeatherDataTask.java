package com.example.FoodDeliveryDemoApp.scheduler;

import com.example.FoodDeliveryDemoApp.component.WeatherDataComponent;
import jakarta.annotation.PostConstruct;
import jakarta.xml.bind.JAXBException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataTask {

    private final WeatherDataComponent weatherDataComponent;

    public WeatherDataTask(WeatherDataComponent weatherDataComponent) {
        this.weatherDataComponent = weatherDataComponent;
    }

    @PostConstruct
    private void onStartup() throws JAXBException {
        saveWeatherDataFromService();
    }

    @Scheduled(cron = "${weather.data.cron-interval}")
    private void onSchedule() throws JAXBException {
        saveWeatherDataFromService();
    }

    private void saveWeatherDataFromService() throws JAXBException {
        weatherDataComponent.getAndSaveWeatherDataFromService();
    }

}

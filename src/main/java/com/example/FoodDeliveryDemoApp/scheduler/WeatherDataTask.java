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
    public void onStartup() throws JAXBException {
        saveWeatherDataFromService();
    }

    @Scheduled(cron = "${weather.data.cron-interval}")
    public void onSchedule() throws JAXBException {
        saveWeatherDataFromService();
    }

    public void saveWeatherDataFromService() throws JAXBException {
        weatherDataComponent.saveWeatherData(weatherDataComponent.getWeatherDataFromService());
    }

}

package com.example.FoodDeliveryDemoApp.scheduler;

import com.example.FoodDeliveryDemoApp.service.WeatherDataService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataTask {

    private final WeatherDataService weatherDataService;

    public WeatherDataTask(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

/*    @Scheduled(cron = "${weatherman.robot.cron_interval}")
    public void doTheTask() {
        weatherDataService.retrieveWeatherObservations();
    }*/

}

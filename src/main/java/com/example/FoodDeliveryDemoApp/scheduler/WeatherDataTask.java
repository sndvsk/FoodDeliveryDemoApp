//package com.example.FoodDeliveryDemoApp.scheduler;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Component
//public class WeatherDataTask {
//
//    @Value("${weather.data.baseurl}")
//    private String weatherObservationsUrl;
//
//    @Value("${weather.data.uri}")
//    private String weatherObservationsUri;
//
//    private final WebClient webClient;
//
//    public WeatherDataTask() {
//        this.webClient = WebClient.builder().baseUrl(weatherObservationsUrl).build();
//    }
//
//    @Scheduled(cron = "${weatherman.robot.cron_interval}")
//    public void retrieveWeatherObservations() {
//        String content = webClient.get()
//                .uri(weatherObservationsUrl)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//
//        System.out.println(content);
//    }
//}

package com.example.FoodDeliveryDemoApp.controller;

import com.example.FoodDeliveryDemoApp.model.OrderData;
import com.example.FoodDeliveryDemoApp.model.WeatherData;
import com.example.FoodDeliveryDemoApp.repository.OrderDataRepository;
import com.example.FoodDeliveryDemoApp.repository.WeatherDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery-fee")
public class DeliveryFeeController {

    private final WeatherDataRepository weatherDataRepository;
    private final OrderDataRepository orderDataRepository;

    public DeliveryFeeController(WeatherDataRepository weatherDataRepository, OrderDataRepository orderDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
        this.orderDataRepository = orderDataRepository;
    }

    @GetMapping() //localhost:8080/delivery-fee?city=__enter-city__&vehicleType=__enter-vehicle__
    public ResponseEntity<OrderData> getDeliveryFee(@RequestParam String city, @RequestParam String vehicleType) {
        // Calculate the delivery fee based on the city and vehicle type
        double deliveryFee = calculateDeliveryFee(city, vehicleType);

        // Create a response object
        OrderData orderData = new OrderData();
        orderData.setDeliveryFee(deliveryFee);

        //orderDataRepository.save(orderData);

        return new ResponseEntity<>(orderData, HttpStatus.OK);
    }

    private double calculateDeliveryFee(String city, String vehicleType) {
        WeatherData weatherData = weatherDataRepository.findTopByStationName(city);
        // TODO
        // calculate delivery fee
        return 0.0;
    }
}

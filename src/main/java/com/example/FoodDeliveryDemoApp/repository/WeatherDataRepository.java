package com.example.FoodDeliveryDemoApp.repository;

import com.example.FoodDeliveryDemoApp.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    WeatherData findFirstByStationNameOrderByTimestampDesc(String stationName);

    List<WeatherData> findByTimestamp(Instant timestamp);

    WeatherData findTopByOrderByIdDesc();
}

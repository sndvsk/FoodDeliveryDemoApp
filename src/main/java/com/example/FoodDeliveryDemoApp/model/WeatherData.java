package com.example.FoodDeliveryDemoApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@SuppressWarnings("unused")
@Entity
@Table(name = "weather_data")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "station_name")
    private String stationName;

    @Column(name = "wmo_code")
    private String wmoCode;

    @Column(name = "air_temperature")
    public Double airTemperature;

    @Column(name = "wind_speed")
    public Double windSpeed;

    @Column(name = "weather_phenomenon")
    public String weatherPhenomenon;

    @JsonIgnore
    @Column(name = "timestamp")
    private Instant timestamp;

    @JsonProperty("timestamp")
    private OffsetDateTime rest_timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName.toLowerCase(Locale.ROOT);
    }

    public String getWmoCode() {
        return wmoCode;
    }

    public void setWmoCode(String wmoCode) {
        this.wmoCode = wmoCode;
    }

    public Double getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherPhenomenon() {
        return weatherPhenomenon;
    }

    public void setWeatherPhenomenon(String weatherPhenomenon) {
        this.weatherPhenomenon = weatherPhenomenon;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Convert Instant to OffsetDateTime to allow client input datetime with server offset.
     * <p>
     *  timestamp - UTC in the database, is not shown on client side
     * rest_timestamp - OffsetDateTime to show on client side so offset of a server can be inputted.
     * </p>
     * @param timestamp time in UTC that will be converted to OffsetDateTime
     */
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp.truncatedTo(ChronoUnit.SECONDS);
        this.rest_timestamp = OffsetDateTime.ofInstant(this.timestamp, ZoneId.systemDefault());
    }

}
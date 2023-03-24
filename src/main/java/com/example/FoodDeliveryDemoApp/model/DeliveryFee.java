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
@Table(name = "delivery_fee")
public class DeliveryFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    //@JsonIgnore
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "delivery_fee")
    private double deliveryFee;

    @JsonIgnore
    @Column(name = "weather_id")
    private Long weatherId;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city.toLowerCase(Locale.ROOT);
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType.toLowerCase(Locale.ROOT);
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Long getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Long weatherId) {
        this.weatherId = weatherId;
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

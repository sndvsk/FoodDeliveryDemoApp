package com.example.FoodDeliveryDemoApp.model.rules;

import jakarta.persistence.*;

@Entity
@Table(name = "base_fee_rules")
public class RegionalBaseFeeRule implements FeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    //@JsonIgnore
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "regional_base_fee")
    private Double fee;

    public RegionalBaseFeeRule(String city, String vehicleType, Double fee) {
        this.city = city;
        this.vehicleType = vehicleType;
        this.fee = fee;
    }

    public RegionalBaseFeeRule() {

    }

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
        this.city = city;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public Double getFee() {
        return fee;
    }

    @Override
    public void setFee(Double fee) {
        this.fee = fee;
    }
}

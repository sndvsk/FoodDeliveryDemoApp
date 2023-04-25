package com.example.FoodDeliveryDemoApp.domain.rules;

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
    public String city;

    @Column(name = "wmo_code")
    public Long wmoCode;

    @Column(name = "vehicle_type")
    public String vehicleType;

    @Column(name = "regional_base_fee")
    public Double fee;

    public RegionalBaseFeeRule(String city, Long wmoCode, String vehicleType, Double fee) {
        this.city = city;
        this.wmoCode = wmoCode;
        this.vehicleType = vehicleType;
        this.fee = fee;
    }

    public RegionalBaseFeeRule() {

    }

    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getWmoCode() {
        return wmoCode;
    }

    public void setWmoCode(Long wmoCode) {
        this.wmoCode = wmoCode;
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

package com.example.FoodDeliveryDemoApp.model.rules.ExtraFee;

import jakarta.persistence.*;

@Entity
@Table(name = "extra_fee_rules_temperature")
public class ExtraFeeAirTemperatureRule extends ExtraFeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    //@JsonIgnore
    private Long id;

    @Column(name = "start_temperature_range")
    private Double startAirTemperatureRange;

    @Column(name = "end_temperature_range")
    private Double endAirTemperatureRange;

    @Column(name = "temperature_fee")
    private Double fee;

    public ExtraFeeAirTemperatureRule(Double startAirTemperatureRange, Double endAirTemperatureRange, Double fee) {
        this.startAirTemperatureRange = startAirTemperatureRange;
        this.endAirTemperatureRange = endAirTemperatureRange;
        this.fee = fee;
    }

    public ExtraFeeAirTemperatureRule() {

    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Double getStartAirTemperatureRange() {
        return startAirTemperatureRange;
    }

    public void setStartAirTemperatureRange(Double startAirTemperatureRange) {
        this.startAirTemperatureRange = startAirTemperatureRange;
    }

    public Double getEndAirTemperatureRange() {
        return endAirTemperatureRange;
    }

    public void setEndAirTemperatureRange(Double endAirTemperatureRange) {
        this.endAirTemperatureRange = endAirTemperatureRange;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}

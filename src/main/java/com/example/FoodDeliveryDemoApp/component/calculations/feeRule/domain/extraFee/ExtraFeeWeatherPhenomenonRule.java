package com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.FeeRule;
import jakarta.persistence.*;

@Entity
@Table(name = "extra_fee_rules_weather_phenomenon")
public class ExtraFeeWeatherPhenomenonRule implements FeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    //@JsonIgnore
    private Long id;

    @Column(name = "weather_phenomenon_name")
    public String weatherPhenomenonName;

    @Column(name = "weather_phenomenon_fee")
    public Double fee;

    public ExtraFeeWeatherPhenomenonRule(String weatherPhenomenonName, Double fee) {
        this.weatherPhenomenonName = weatherPhenomenonName;
        this.fee = fee;
    }

    public ExtraFeeWeatherPhenomenonRule() {
    }

    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    public String getWeatherPhenomenonName() {
        return weatherPhenomenonName;
    }

    public void setWeatherPhenomenonName(String weatherPhenomenonName) {
        this.weatherPhenomenonName = weatherPhenomenonName;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}

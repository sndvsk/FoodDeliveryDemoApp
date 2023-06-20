package com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.FeeRule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}

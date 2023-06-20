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
@Table(name = "extra_fee_rules_temperature")
public class ExtraFeeAirTemperatureRule implements FeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    //@JsonIgnore
    private Long id;

    @Column(name = "start_temperature_range")
    public Double startAirTemperatureRange;

    @Column(name = "end_temperature_range")
    public Double endAirTemperatureRange;

    @Column(name = "temperature_fee")
    public Double fee;

    public ExtraFeeAirTemperatureRule(Double startAirTemperatureRange, Double endAirTemperatureRange, Double fee) {
        this.startAirTemperatureRange = startAirTemperatureRange;
        this.endAirTemperatureRange = endAirTemperatureRange;
        this.fee = fee;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}

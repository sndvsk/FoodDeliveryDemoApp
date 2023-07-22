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
@Table(name = "extra_fee_rules_windspeed")
public class ExtraFeeWindSpeedRule implements FeeRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    //@JsonIgnore
    private Long id;

    @Column(name = "start_windspeed_range")
    public Double startWindSpeedRange;

    @Column(name = "end_windspeed_range")
    public Double endWindSpeedRange;

    @Column(name = "windspeed_fee")
    public Double fee;

    public ExtraFeeWindSpeedRule(Double startWindSpeedRange, Double endWindSpeedRange, Double fee) {
        this.startWindSpeedRange = startWindSpeedRange;
        this.endWindSpeedRange = endWindSpeedRange;
        this.fee = fee;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

}

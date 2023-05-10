package com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.extraFee;

import com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain.FeeRule;
import jakarta.persistence.*;

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

    public ExtraFeeWindSpeedRule() {
    }

    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    public Double getStartWindSpeedRange() {
        return startWindSpeedRange;
    }

    public void setStartWindSpeedRange(Double startWindSpeedRange) {
        this.startWindSpeedRange = startWindSpeedRange;
    }

    public Double getEndWindSpeedRange() {
        return endWindSpeedRange;
    }

    public void setEndWindSpeedRange(Double endWindSpeedRange) {
        this.endWindSpeedRange = endWindSpeedRange;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

}

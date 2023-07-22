package com.example.FoodDeliveryDemoApp.component.calculations.feeRule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    @Override
    public Double getFee() {
        return fee;
    }

    @Override
    public void setFee(Double fee) {
        this.fee = fee;
    }

}

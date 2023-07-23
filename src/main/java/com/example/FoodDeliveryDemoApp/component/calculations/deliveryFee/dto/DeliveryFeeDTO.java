package com.example.FoodDeliveryDemoApp.component.calculations.deliveryFee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryFeeDTO {

    @JsonProperty("fee_id")
    private Long id;

    @JsonProperty("city")
    private String city;

    @JsonProperty("vehicle_type")
    private String vehicleType;

    @JsonProperty("delivery_fee")
    private double deliveryFee;

    @JsonProperty("timestamp")
    private OffsetDateTime rest_timestamp;

}

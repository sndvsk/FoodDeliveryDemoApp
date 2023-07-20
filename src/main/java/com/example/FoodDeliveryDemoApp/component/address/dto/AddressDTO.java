package com.example.FoodDeliveryDemoApp.component.address.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    @JsonProperty("country")
    private String country;

    @JsonProperty("county")
    private String county;

    @JsonProperty("city")
    private String city;

    @JsonProperty("zip_code")
    private Integer zipCode;

    @JsonProperty("street")
    private String street;

    @JsonProperty("house_number")
    private String houseNumber;

    @JsonProperty("apt_number")
    private String aptNumber;

}

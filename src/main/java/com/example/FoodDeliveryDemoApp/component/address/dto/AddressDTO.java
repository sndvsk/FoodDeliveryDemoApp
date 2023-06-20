package com.example.FoodDeliveryDemoApp.component.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String country;
    private String county;
    private String city;
    private String zipCode;
    private String street;
    private String houseNumber;
    private String aptNumber;

}

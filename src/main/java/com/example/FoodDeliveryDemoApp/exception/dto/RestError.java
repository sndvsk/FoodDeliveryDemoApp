package com.example.FoodDeliveryDemoApp.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestError {

    @JsonProperty("errorCode")
    private int errorCode;
    @JsonProperty("errorMessage")
    private String errorMessage;

}

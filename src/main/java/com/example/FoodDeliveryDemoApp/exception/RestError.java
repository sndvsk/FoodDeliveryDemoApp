package com.example.FoodDeliveryDemoApp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestError {

    private String errorCode;
    private String errorMessage;

}

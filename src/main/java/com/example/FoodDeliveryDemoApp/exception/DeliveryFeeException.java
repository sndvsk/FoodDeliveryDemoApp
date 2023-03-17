package com.example.FoodDeliveryDemoApp.exception;


import java.util.List;

public class DeliveryFeeException extends RuntimeException {

    private List<DeliveryFeeException> exceptions;


    public DeliveryFeeException(String message) {
        super(message);
    }

    public DeliveryFeeException(List<DeliveryFeeException> exceptions) {
        this.exceptions = exceptions;
    }

    public List<DeliveryFeeException> getExceptions() {
        return exceptions;
    }
}

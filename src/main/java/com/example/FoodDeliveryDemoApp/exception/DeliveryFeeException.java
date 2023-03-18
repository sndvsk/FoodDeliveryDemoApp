package com.example.FoodDeliveryDemoApp.exception;


import java.util.List;

public class DeliveryFeeException extends RuntimeException {

    @SuppressWarnings("unused")
    private List<DeliveryFeeException> exceptions;


    public DeliveryFeeException(String message) {
        super(message);
    }

    public DeliveryFeeException(List<DeliveryFeeException> exceptions) {
        super(exceptions.get(0));
    }

    @SuppressWarnings("unused")
    public List<DeliveryFeeException> getExceptions() {
        return exceptions;
    }

}

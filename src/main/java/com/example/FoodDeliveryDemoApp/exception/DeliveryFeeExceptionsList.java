package com.example.FoodDeliveryDemoApp.exception;

import java.util.List;

public class DeliveryFeeExceptionsList extends Exception {

    private final List<DeliveryFeeException> exceptions;

    public DeliveryFeeExceptionsList(List<DeliveryFeeException> exceptions) {
        this.exceptions = exceptions;
    }

    @SuppressWarnings("unused")
    public List<DeliveryFeeException> getExceptions() {
        return exceptions;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("DeliveryFeeExceptionsList: [");
        for (DeliveryFeeException ex : exceptions) {
            sb.append(ex.getMessage()).append("; ");
        }
        sb.append("]");
        return sb.toString();
    }
}

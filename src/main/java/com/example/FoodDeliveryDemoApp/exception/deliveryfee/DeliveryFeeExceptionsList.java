package com.example.FoodDeliveryDemoApp.exception.deliveryfee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class DeliveryFeeExceptionsList extends RuntimeException {

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

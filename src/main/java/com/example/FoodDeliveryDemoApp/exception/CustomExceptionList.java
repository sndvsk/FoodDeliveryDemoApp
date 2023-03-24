package com.example.FoodDeliveryDemoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class CustomExceptionList extends IllegalArgumentException {

    private final List<CustomBadRequestException> exceptions;

    public CustomExceptionList(List<CustomBadRequestException> exceptions) {
        this.exceptions = exceptions;
    }

    @SuppressWarnings("unused")
    public List<CustomBadRequestException> getExceptions() {
        return exceptions;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("DeliveryFeeExceptionsList: [");
        for (CustomBadRequestException ex : exceptions) {
            sb.append(ex.getMessage()).append("; ");
        }
        sb.append("]");
        return sb.toString();
    }

}

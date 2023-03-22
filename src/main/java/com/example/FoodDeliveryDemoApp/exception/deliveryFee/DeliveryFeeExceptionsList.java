package com.example.FoodDeliveryDemoApp.exception.deliveryFee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class DeliveryFeeExceptionsList extends IllegalArgumentException {

    // todo rename to CustomBadRequestExceptionList

    private final List<DeliveryFeeBadRequestException> exceptions;

    public DeliveryFeeExceptionsList(List<DeliveryFeeBadRequestException> exceptions) {
        this.exceptions = exceptions;
    }

    @SuppressWarnings("unused")
    public List<DeliveryFeeBadRequestException> getExceptions() {
        return exceptions;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("DeliveryFeeExceptionsList: [");
        for (DeliveryFeeBadRequestException ex : exceptions) {
            sb.append(ex.getMessage()).append("; ");
        }
        sb.append("]");
        return sb.toString();
    }

}

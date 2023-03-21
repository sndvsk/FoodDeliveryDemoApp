package com.example.FoodDeliveryDemoApp.exception.deliveryfee;


import org.assertj.core.util.VisibleForTesting;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Objects;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DeliveryFeeException extends RuntimeException {

    @SuppressWarnings("unused")
    private List<DeliveryFeeException> exceptions;

    private String message;

    public DeliveryFeeException(String message) {
        super(message);
        this.message = message;
    }

    public DeliveryFeeException(List<DeliveryFeeException> exceptions) {
        super(exceptions.get(0).getMessage());
    }

    @SuppressWarnings("unused")
    public List<DeliveryFeeException> getExceptions() {
        return exceptions;
    }

    @VisibleForTesting
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryFeeException that = (DeliveryFeeException) o;
        return Objects.equals(message, that.message);
    }

    @VisibleForTesting
    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

}

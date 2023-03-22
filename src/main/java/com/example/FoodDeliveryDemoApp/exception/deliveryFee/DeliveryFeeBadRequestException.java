package com.example.FoodDeliveryDemoApp.exception.deliveryFee;


import org.assertj.core.util.VisibleForTesting;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Objects;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DeliveryFeeBadRequestException extends IllegalArgumentException {

    // todo rename to CustomBadRequestException

    @SuppressWarnings("unused")
    private List<DeliveryFeeBadRequestException> exceptions;

    private String message;

    public DeliveryFeeBadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public DeliveryFeeBadRequestException(List<DeliveryFeeBadRequestException> exceptions) {
        super(exceptions.get(0).getMessage());
    }

    @SuppressWarnings("unused")
    public List<DeliveryFeeBadRequestException> getExceptions() {
        return exceptions;
    }

    @VisibleForTesting
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryFeeBadRequestException that = (DeliveryFeeBadRequestException) o;
        return Objects.equals(message, that.message);
    }

    @VisibleForTesting
    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

}

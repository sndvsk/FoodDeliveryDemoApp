package com.example.FoodDeliveryDemoApp.exception;


import org.assertj.core.util.VisibleForTesting;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Objects;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomBadRequestException extends IllegalArgumentException {

    @SuppressWarnings("unused")
    private List<CustomBadRequestException> exceptions;

    private String message;

    public CustomBadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public CustomBadRequestException(List<CustomBadRequestException> exceptions) {
        super(exceptions.get(0).getMessage());
    }

    @SuppressWarnings("unused")
    public List<CustomBadRequestException> getExceptions() {
        return exceptions;
    }

    @VisibleForTesting
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomBadRequestException that = (CustomBadRequestException) o;
        return Objects.equals(message, that.message);
    }

    @VisibleForTesting
    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

}

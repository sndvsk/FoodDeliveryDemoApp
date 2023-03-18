package com.example.FoodDeliveryDemoApp.exception;


import java.util.List;
import java.util.Objects;

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

    /**
     * Returns true if this DeliveryFeeException object is equal to the specified object.
     *
     * Method is overridden because this implementation of equals ensures that two DeliveryFeeException
     * objects are considered equal if their message fields are equal.
     * This is useful when comparing exceptions in unit tests or when handling exception scenarios in a program.
     *
     * @param o the object to compare this DeliveryFeeException against
     * @return true if the given object is equal to this DeliveryFeeException, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryFeeException that = (DeliveryFeeException) o;
        return Objects.equals(message, that.message);
    }

    /**
     * Returns the hash code value for this DeliveryFeeException object.
     * The hash code is calculated using the message field, which is the only field used for equality comparison in the equals() method.
     *
     * Method is overridden to generate a unique hash code for the DeliveryFeeException object based on its message field.
     *
     * @return the hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

}

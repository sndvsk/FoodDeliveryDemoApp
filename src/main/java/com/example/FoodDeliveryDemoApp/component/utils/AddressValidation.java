package com.example.FoodDeliveryDemoApp.component.utils;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;

import java.util.Optional;

public class AddressValidation {

    private final static String unicodeLettersAndSpacesPattern = "^[\\p{L} ']+$";
    private final static String unicodeLettersDigitsSpacesPattern = "^[\\p{L}\\p{N} ]*$";
    private final static String unicodeLettersDigitsPattern = "^[\\p{L}\\p{N}]*$";
    private final static String houseNumberPattern = "^\\d+[-\\s/]*\\d*$";
    private final static String zipCodePattern = "^\\d{5,15}$";

    public static void validateCreateAddress(AddressDTO request) {
        validateAddressFields(request);
    }

    public static void validateUpdateAddress(AddressDTO updatedAddress) {
        validateOptional(updatedAddress.getCountry(), name ->
                validatePattern(name, "country", unicodeLettersAndSpacesPattern, 3, 255));
        validateOptional(updatedAddress.getCounty(), name ->
                validatePattern(name, "county", unicodeLettersAndSpacesPattern, 3, 255));
        validateOptional(updatedAddress.getCity(), name ->
                validatePattern(name, "city", unicodeLettersAndSpacesPattern, 3, 255));
        validateOptional(updatedAddress.getZipCode().toString(), name ->
                validatePattern(name, "zip_code", zipCodePattern, 5, 15));
        validateOptional(updatedAddress.getStreet(), name ->
                validatePattern(name, "street",
                        unicodeLettersDigitsSpacesPattern, 3, 255));
        validateOptional(updatedAddress.getHouseNumber(), name ->
                validatePattern(name, "house_number", houseNumberPattern, 1, 255));
        validateOptional(updatedAddress.getAptNumber(), name ->
                validatePattern(name, "apt_number", unicodeLettersDigitsPattern, 1, 255));
    }

    private static void validateAddressFields(AddressDTO request) {
        validatePattern(request.getCountry(),
                "country", unicodeLettersAndSpacesPattern, 3, 255);
        validatePattern(request.getCounty(),
                "county", unicodeLettersAndSpacesPattern, 3, 255);
        validatePattern(request.getCity(),
                "city", unicodeLettersAndSpacesPattern, 3, 255);
        validatePattern(request.getZipCode().toString(),
                "zip_code", zipCodePattern, 5, 15);
        validatePattern(request.getStreet(),
                "street", unicodeLettersDigitsSpacesPattern, 3, 255);
        validatePattern(request.getHouseNumber(),
                "house_number", houseNumberPattern, 1, 255);
        // For optional fields in createAddress, you might want to check if they're present first.
        if (request.getAptNumber() != null && !request.getAptNumber().isEmpty()) {
            validatePattern(request.getAptNumber(),
                    "apt_number", unicodeLettersDigitsPattern, 1, 255);
        }
    }

    private static void validatePattern(String value, String fieldName, String pattern, int minLength, int maxLength) {
        if (!value.matches(pattern) || value.length() < minLength || value.length() > maxLength) {
            throw new CustomBadRequestException("Invalid " + fieldName + ".");
        }
    }

    private static <T> void validateOptional(T value, UserDetailValidation.ValidationFunction<T> validator) {
        Optional.ofNullable(value).ifPresent(validator::validate);
    }
}


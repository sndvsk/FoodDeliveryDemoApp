package com.example.FoodDeliveryDemoApp.component.utils;


import com.example.FoodDeliveryDemoApp.exception.CustomAccessDeniedException;

import java.util.Objects;

public class OwnershipHelper {

    public static void validateOwner(Long providedOwnerId, Long actualOwnerId) {
        validateIdMatch(providedOwnerId, actualOwnerId, "Actual owner id is not matching with provided owner id.");
    }

    public static void validateMenu(Long providedOwnerId, Long actualOwnerId) {
        validateIdMatch(providedOwnerId, actualOwnerId,
                "Actual menu id is not matching with provided menu id.");
    }

    public static void validateRestaurant(Long providedOwnerId, Long actualOwnerId) {
        validateIdMatch(providedOwnerId, actualOwnerId,
                "Actual restaurant id is not matching with provided restaurant id.");
    }

    public static void validateCustomer(Long providedCustomerId, Long actualCustomerId) {
        validateIdMatch(providedCustomerId, actualCustomerId,
                "Actual customer id is not matching with provided customer id.");
    }

    private static void validateIdMatch(Long id1, Long id2, String exceptionMessage) {
        if (!Objects.equals(id1, id2)) {
            throw new CustomAccessDeniedException(exceptionMessage);
        }
    }
}



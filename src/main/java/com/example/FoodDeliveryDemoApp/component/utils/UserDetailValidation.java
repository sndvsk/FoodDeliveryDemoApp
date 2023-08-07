package com.example.FoodDeliveryDemoApp.component.utils;

import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.AuthenticationRequest;
import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.RegisterRequest;
import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.UserDTO;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;

import java.util.Optional;

public class UserDetailValidation {

    private final static String namePattern = "^[\\p{L} ']+$"; // This regex matches any Unicode letter and spaces
    private final static String emailPattern  = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    private final static String telephonePattern = "^\\+?[1-9]\\d{1,15}$";

    public static void validateLogin(AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        validateStringSize(username, "username");
        validateStringSize(password, "password");
    }

    public static void validateRegisterUser(RegisterRequest request) {
        validateName(request.getFirstname(), "firstname");
        validateName(request.getLastname(), "lastname");
        validateStringSize(request.getUsername(), "username");
        validateStringSize(request.getPassword(), "password");
        validateEmail(request.getEmail());
        validateTelephone(request.getTelephone());
    }

    public static void validateUpdateUser(UserDTO updatedUser) {
        validateOptional(updatedUser.getFirstname(), name -> validateName(name, "firstname"));
        validateOptional(updatedUser.getLastname(), name -> validateName(name, "lastname"));
        validateOptional(updatedUser.getUsername(), name -> validateStringSize(name, "username"));
        validateOptional(updatedUser.getEmail(), UserDetailValidation::validateEmail);
        validateOptional(updatedUser.getPassword(), password -> validateStringSize(password, "password"));
        validateOptional(updatedUser.getTelephone(), UserDetailValidation::validateTelephone);
    }


    private static void validateName(String name, String fieldName) {
        if (!name.matches(namePattern) || name.length() < 3 || name.length() > 255) {
            throw new CustomBadRequestException("Invalid " + fieldName + ".");
        }
    }

    public static void validateStringSize(String value, String fieldName) {
        if (value.length() < 3 || value.length() > 255) {
            throw new CustomBadRequestException("Invalid " + fieldName + ".");
        }
    }

    private static void validateEmail(String email) {
        if (!email.matches(emailPattern) || email.length() > 255) {
            throw new CustomBadRequestException("Invalid email.");
        }
    }

    private static void validateTelephone(String telephone) {
        if (!telephone.matches(telephonePattern) || telephone.length() > 255) {
            throw new CustomBadRequestException("Invalid telephone number.");
        }
    }

    @FunctionalInterface
    public interface ValidationFunction<T> {
        void validate(T value) throws CustomBadRequestException;
    }

    private static <T> void validateOptional(T value, ValidationFunction<T> validator) {
        Optional.ofNullable(value).ifPresent(validator::validate);
    }
}

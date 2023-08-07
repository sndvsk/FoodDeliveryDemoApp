package com.example.FoodDeliveryDemoApp.component.userItems.user.dto;

import com.example.FoodDeliveryDemoApp.component.userItems.Role;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @JsonProperty("firstname")
    @NotBlank
    @Size(min = 3, max = 255)
    @Pattern(regexp = "^[\\p{L} ']+$", message = "First name can only contain Unicode letters and spaces.")
    private String firstname;

    @JsonProperty("lastname")
    @NotBlank
    @Size(min = 3, max = 255)
    @Pattern(regexp = "^[\\p{L} ']+$", message = "Last name can only contain Unicode letters and spaces.")
    private String lastname;

    @JsonProperty("username")
    @NotBlank
    @Size(min = 3, max = 255)
    private String username;

    @JsonProperty("email")
    @NotBlank
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = "Invalid email format.")
    @Size(max = 255)
    private String email;

    @JsonProperty("password")
    @NotBlank
    @Size(min = 3, max = 255)
    private String password;

    @JsonProperty("telephone")
    @NotBlank
    @Size(max = 255)
    @Pattern(regexp = "^\\+?[1-9]\\d{1,15}$", message = "Invalid telephone number format.")
    private String telephone;

    @JsonProperty("role")
    private Role role;

    public Role getRole() {
        return Role.valueOf(role.name().toUpperCase());
    }
}

package com.example.FoodDeliveryDemoApp.component.userItems.user.dto;

import com.example.FoodDeliveryDemoApp.component.userItems.Role;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("telephone")
    private String telephone;

    @JsonProperty("role")
    private Role role;

    public Role getRole() {
        return Role.valueOf(role.name().toUpperCase());
    }

}

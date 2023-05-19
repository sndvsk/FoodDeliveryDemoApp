package com.example.FoodDeliveryDemoApp.security.auth.dto;

import com.example.FoodDeliveryDemoApp.component.userItems.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String username;
    private String lastname;
    private String email;
    private String password;
    private Roles role;

}

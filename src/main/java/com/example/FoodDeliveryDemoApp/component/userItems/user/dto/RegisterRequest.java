package com.example.FoodDeliveryDemoApp.component.userItems.user.dto;

import com.example.FoodDeliveryDemoApp.component.userItems.Role;
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
    private String lastname;
    private String username;
    private String email;
    private String password;
    private Role role;

}

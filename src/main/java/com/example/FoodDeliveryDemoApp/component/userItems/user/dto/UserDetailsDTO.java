package com.example.FoodDeliveryDemoApp.component.userItems.user.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

}


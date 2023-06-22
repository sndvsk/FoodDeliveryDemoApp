package com.example.FoodDeliveryDemoApp.component.userItems.admin.dto;

import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {

    @JsonProperty("level")
    private Long level;

    @JsonProperty("user")
    private UserDTO userDTO;

}

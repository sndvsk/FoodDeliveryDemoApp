package com.example.FoodDeliveryDemoApp.component.userItems.owner.dto;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto.RestaurantDTO;
import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDTO {

    @JsonProperty("owner_id")
    private Long id;

    @JsonProperty("approved")
    private boolean approved;

    @JsonProperty("user")
    private UserDTO userDTO;

    @JsonProperty("restaurants")
    private List<RestaurantDTO> restaurantDTOs;

}

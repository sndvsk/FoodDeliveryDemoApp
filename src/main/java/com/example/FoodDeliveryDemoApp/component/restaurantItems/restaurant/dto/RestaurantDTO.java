package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto.MenuDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    @JsonProperty("restaurant_id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("theme")
    private String theme;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("image")
    private String image;

    @JsonProperty("owner_id")
    private Long ownerId;

    @JsonProperty("address")
    private AddressDTO address;

    @JsonProperty("menus")
    private List<MenuDTO> menus;

}

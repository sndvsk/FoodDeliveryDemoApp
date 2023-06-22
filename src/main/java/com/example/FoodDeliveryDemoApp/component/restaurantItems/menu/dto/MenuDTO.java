package com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.dto;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto.ItemDTO;
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
public class MenuDTO {

    @JsonProperty("menu_id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("hidden")
    private String hidden;

    @JsonProperty("owner_id")
    private Long ownerId;

    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @JsonProperty("items")
    private List<ItemDTO> items;

}

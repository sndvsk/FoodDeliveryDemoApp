package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.dto;

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
public class ItemDTO {

    @JsonProperty("item_id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("image")
    private String image;

    @JsonProperty("ingredients")
    private List<String> ingredients;

    @JsonProperty("allergens")
    private List<String> allergens;

    @JsonProperty("menu_id")
    private Long menuId;

}

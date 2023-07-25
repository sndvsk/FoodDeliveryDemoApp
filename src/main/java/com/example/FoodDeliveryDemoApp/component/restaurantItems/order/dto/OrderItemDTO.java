package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    @JsonProperty("item_id")
    private Long itemId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("order_id")
    private Long orderId;
}

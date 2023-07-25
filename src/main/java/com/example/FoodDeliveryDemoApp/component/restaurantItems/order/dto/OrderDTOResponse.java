package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTOResponse {

    @JsonProperty("order_id")
    private Long id;

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("restaurant_id")
    private Long restaurantId;

    @JsonProperty("restaurant_name")
    private String restaurantName;

    @JsonProperty("datetime")
    private OffsetDateTime orderDate;

    @JsonProperty("item_price")
    private Double itemPrice;

    @JsonProperty("delivery_fee")
    private Double deliveryFee;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("status")
    private String status;

    @JsonProperty("items")
    private List<OrderItemDTOResponse> items;
}

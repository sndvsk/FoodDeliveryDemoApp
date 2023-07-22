package com.example.FoodDeliveryDemoApp.component.userItems.customer.dto;

import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto.OrderDTO;
import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    @JsonProperty("user")
    private UserDTO userDTO;

    @JsonProperty("orders")
    private List<OrderDTO> orderDTOs;

    @JsonProperty("address")
    private AddressDTO addressDTO;

}

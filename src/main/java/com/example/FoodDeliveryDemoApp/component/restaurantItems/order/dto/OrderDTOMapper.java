package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.dto;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.Order;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTOMapper {

    public static OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCustomerId(order.getCustomer().getId());
        dto.setRestaurantId(order.getRestaurant().getId());
        dto.setOrderDate(OffsetDateTime.ofInstant(order.getOrderDate(), ZoneId.systemDefault()));
        dto.setItemPrice(order.getItemPrice());
        dto.setDeliveryFee(order.getDeliveryFee());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus().name());

        List<Long> items = order.getItems().stream()
                .map(Item::getId)
                .collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }

    public static List<OrderDTO> toDtoList(List<Order> orders) {
        return orders.stream()
                .map(OrderDTOMapper::toDto)
                .collect(Collectors.toList());
    }

}

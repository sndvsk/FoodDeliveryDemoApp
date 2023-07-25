package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "quantity")
    private int quantity;

    public OrderItem(OrderItemId id, Order order, Item item, int quantity) {
        this.id = new OrderItemId(order.getId(), item.getId());
        this.order = order;
        this.item = item;
        this.quantity = quantity;
    }

    public OrderItem(Order order, Item item, int quantity) {
        this.order = order;
        this.item = item;
        this.quantity = quantity;
    }

}


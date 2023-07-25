package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "datetime")
    private Instant orderDate;

    @Column(name = "item_price")
    private Double itemPrice;

    @Column(name = "delivery_fee")
    private Double deliveryFee;

    @Column(name = "total_price")
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(Long id,
                 Customer customer,
                 Restaurant restaurant,
                 Instant orderDate,
                 Double totalPrice,
                 OrderStatus status) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order(Customer customer, Restaurant restaurant, Instant orderDate, Double totalPrice, OrderStatus status) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order(Customer customer, Restaurant restaurant) {
        this.customer = customer;
        this.restaurant = restaurant;
    }

}

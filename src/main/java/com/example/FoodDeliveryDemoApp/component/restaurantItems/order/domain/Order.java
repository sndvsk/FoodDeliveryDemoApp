package com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "status")
    private String status;

    @ManyToMany
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    public Order(Long id,
                 Customer customer,
                 Restaurant restaurant,
                 Instant orderDate,
                 Double totalPrice,
                 String status) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order(Customer customer, Restaurant restaurant, Instant orderDate, Double totalPrice, String status) {
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

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        Double price = 0.0;
        for (Item item: items) {
            price += item.getPrice();
        }
        return price;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

}

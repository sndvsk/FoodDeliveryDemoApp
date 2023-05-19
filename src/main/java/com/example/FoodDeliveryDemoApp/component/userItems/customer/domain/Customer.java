package com.example.FoodDeliveryDemoApp.component.userItems.customer.domain;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.Order;
import com.example.FoodDeliveryDemoApp.component.userItems.Roles;
import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.User;
import com.example.FoodDeliveryDemoApp.security.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_customers")
public class Customer extends User {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToOne(mappedBy = "customer")
    private Token token;

/*    public Customer(Long id, String username, String password, String email, Roles role, Instant createdAt, Instant updatedAt, List<Order> orders) {
        super(id, username, password, email, role, createdAt, updatedAt);
        this.orders = orders;
    }

    public Customer(List<Order> orders, List<Address> addresses) {
        this.orders = orders;
        this.addresses = addresses;
    }

    public Customer() {
    }*/

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}

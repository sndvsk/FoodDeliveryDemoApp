package com.example.FoodDeliveryDemoApp.component.userItems.owner.domain;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
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
@Table(name = "users_owners")
public class Owner extends User {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants;

    @OneToOne(mappedBy = "owner")
    private Token token;

/*    public Owner(Long id, String username, String password, String email, Roles role, Instant createdAt, Instant updatedAt, List<Restaurant> restaurants) {
        super(id, username, password, email, role, createdAt, updatedAt);
        this.restaurants = restaurants;
    }

    public Owner(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public Owner() {
    }*/

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}

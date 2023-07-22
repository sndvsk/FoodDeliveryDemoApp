package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain.Item;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.Order;
import com.example.FoodDeliveryDemoApp.component.address.domain.Address;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme")
    private RestaurantTheme theme;

    @Column(name = "phone")
    private String phone;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Restaurant(Long id, String name, String description, RestaurantTheme theme,
                      String phone, String image, Owner owner,
                      List<Menu> menus, List<Order> orders, Address address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.phone = phone;
        this.image = image;
        this.owner = owner;
        this.menus = menus;
        this.orders = orders;
        this.address = address;
    }

    public Restaurant(String name, String description, RestaurantTheme theme,
                      String phone, String image, Owner owner, Address address) {
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.phone = phone;
        this.image = image;
        this.owner = owner;
        this.address = address;
    }

    public Restaurant(String name, String description, RestaurantTheme theme, String phone, String image, Address address) {
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.phone = phone;
        this.image = image;
        this.address = address;
    }

    public Restaurant(String name, String description, RestaurantTheme theme, String phone, String image, Owner owner) {
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.phone = phone;
        this.image = image;
        this.owner = owner;
    }

}

package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.Order;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "desc")
    private String description;

    @Column(name = "theme")
    private String theme;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "owner")
    private Owner owner;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Restaurant(Long id, String name, String description, String theme,
                      String address, String phone, String image, Owner owner,
                      List<Menu> menus, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.owner = owner;
        this.menus = menus;
        this.orders = orders;
    }

    public Restaurant(String name, String description, String theme, String address,
                      String phone, String image, Owner owner) {
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.owner = owner;
    }

    public Restaurant(String name, String description, String theme, String address, String phone, String image) {
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.address = address;
        this.phone = phone;
        this.image = image;
    }

    public Restaurant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}

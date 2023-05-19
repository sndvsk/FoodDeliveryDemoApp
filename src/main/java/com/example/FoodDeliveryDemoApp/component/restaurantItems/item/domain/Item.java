package com.example.FoodDeliveryDemoApp.component.restaurantItems.item.domain;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.menu.domain.Menu;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.order.domain.Order;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "desc")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "image")
    private String image;

    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "allergens")
    private String allergens;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public Item(Long id, String name, String description, Double price, String image, String ingredients, String allergens, Menu menu) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.ingredients = ingredients;
        this.allergens = allergens;
        this.menu = menu;
    }

    public Item(String name, String description, Double price, String image, String ingredients, String allergens, Menu menu) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.ingredients = ingredients;
        this.allergens = allergens;
        this.menu = menu;
    }

    public Item(String name, String description, Double price, String image, String ingredients, String allergens) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.ingredients = ingredients;
        this.allergens = allergens;
    }

    public Item() {

    }

    @ManyToMany(mappedBy = "items")
    private List<Order> orders;

    // Other fields, getters and setters...


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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}

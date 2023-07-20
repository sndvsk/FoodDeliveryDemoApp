package com.example.FoodDeliveryDemoApp.component.address.domain;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "county")
    private String county;

    @Column(name = "country")
    private String country;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "apt_number")
    private String aptNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

}

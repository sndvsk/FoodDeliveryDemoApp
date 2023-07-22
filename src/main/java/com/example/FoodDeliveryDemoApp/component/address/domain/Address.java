package com.example.FoodDeliveryDemoApp.component.address.domain;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

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
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", unique = true)
    private Restaurant restaurant;

    public void update(Address newAddress) {
        this.setStreet(newAddress.getStreet());
        this.setCity(newAddress.getCity());
        this.setCounty(newAddress.getCounty());
        this.setCountry(newAddress.getCountry());
        this.setZipCode(newAddress.getZipCode());
        this.setHouseNumber(newAddress.getHouseNumber());
        this.setAptNumber(newAddress.getAptNumber());
    }

    // prevent the creation of new address instance in the repository
    public boolean isAddressEqual(Address other) {
        if (other == null) return false;

        return Objects.equals(street, other.street) &&
                Objects.equals(city, other.city) &&
                Objects.equals(county, other.county) &&
                Objects.equals(country, other.country) &&
                Objects.equals(zipCode, other.zipCode) &&
                Objects.equals(houseNumber, other.houseNumber) &&
                Objects.equals(aptNumber, other.aptNumber);
    }
}

package com.example.FoodDeliveryDemoApp.component.userItems.customer.domain;

import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import jakarta.persistence.*;

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
    private String zipCode;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "apt_number")
    private String aptNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Address(Long id, String street, String city, String county, String country, String zipCode, String houseNumber, String aptNumber, Customer customer) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.county = county;
        this.country = country;
        this.zipCode = zipCode;
        this.houseNumber = houseNumber;
        this.aptNumber = aptNumber;
        this.customer = customer;
    }

    public Address() {
    }

    // Getters and setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAptNumber() {
        return aptNumber;
    }

    public void setAptNumber(String aptNumber) {
        this.aptNumber = aptNumber;
    }

    public Customer getUser() {
        return customer;
    }

    public void setUser(Customer customer) {
        this.customer = customer;
    }
}

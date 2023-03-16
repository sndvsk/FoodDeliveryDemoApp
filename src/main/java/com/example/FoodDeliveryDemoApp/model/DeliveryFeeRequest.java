package com.example.FoodDeliveryDemoApp.model;

public class DeliveryFeeRequest {
    private String city;
    private String vehicleType;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}

package com.example.FoodDeliveryDemoApp.component.address.dto;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.address.domain.Address;

import java.util.List;
import java.util.stream.Collectors;

public class AddressDTOMapper {

    public static AddressDTO toDto(Address address) {
        return AddressDTO.builder()
                .country(address.getCountry())
                .county(address.getCounty())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .aptNumber(address.getAptNumber())
                .build();
    }

    public static Address toEntity(AddressDTO addressDTO, Restaurant restaurant) {
        if (addressDTO == null) {
            return null;
        }

        return Address.builder()
                .country(addressDTO.getCountry())
                .county(addressDTO.getCounty())
                .city(addressDTO.getCity())
                .zipCode(addressDTO.getZipCode())
                .street(addressDTO.getStreet())
                .houseNumber(addressDTO.getHouseNumber())
                .aptNumber(addressDTO.getAptNumber())
                .restaurant(restaurant) // assuming the Address entity has an owner field
                .build();
    }

    public static List<AddressDTO> toDtoList(List<Address> addresses) {
        return addresses.stream()
                .map(AddressDTOMapper::toDto)
                .collect(Collectors.toList());
    }

}

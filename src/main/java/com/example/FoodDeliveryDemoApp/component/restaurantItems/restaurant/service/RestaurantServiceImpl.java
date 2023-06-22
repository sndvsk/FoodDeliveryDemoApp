package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.RestaurantTheme;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto.RestaurantDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto.RestaurantDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository.RestaurantRepository;
import com.example.FoodDeliveryDemoApp.component.address.domain.Address;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTOMapper;
import com.example.FoodDeliveryDemoApp.component.address.repository.AddressRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.service.OwnerService;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RestaurantServiceImpl implements RestaurantService {

    private final OwnerService ownerService;
    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    private final AddressRepository addressRepository;

    public RestaurantServiceImpl(OwnerService ownerService, RestaurantRepository restaurantRepository, OwnerRepository ownerRepository, AddressRepository addressRepository) {
        this.ownerService = ownerService;
        this.restaurantRepository = restaurantRepository;
        this.ownerRepository = ownerRepository;
        this.addressRepository = addressRepository;
    }

    private void validateInputs() {

    }

    private void validateRequiredInputs() {

    }

    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty())
            throw new CustomNotFoundException("No restaurants in the database.");
        return RestaurantDTOMapper.toDtoList(restaurants);
    }

    public List<RestaurantDTO> getRestaurantsByOwnerId(Long ownerId) {
        List<Restaurant> restaurants = ownerRepository.findById(ownerId)
                .map(Owner::getRestaurants)
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with id " + ownerId));

        return RestaurantDTOMapper.toDtoList(restaurants);
    }

    public List<RestaurantDTO> getRestaurantsByTheme(String theme) {
        List<Restaurant> restaurants = restaurantRepository.findByTheme(theme);
        if (restaurants.isEmpty())
            throw new CustomNotFoundException("Restaurant not found with theme " + theme);
        return RestaurantDTOMapper.toDtoList(restaurants);
    }

/*    public Restaurant createRestaurant(String username, String name, String desc, String theme,
                                       String phone, String image, AddressDTO address) {
        Long ownerId = ownerService.getIdByUsername(username);
        return createRestaurant(ownerId, name, desc, theme, phone, image, address);
    }*/


    public RestaurantDTO createRestaurant(Long ownerId, String name, String desc, RestaurantTheme theme,
                                       String phone, String image, AddressDTO addressGiven) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with id " + ownerId));

        // Create the restaurant first without the address
        Restaurant restaurant = new Restaurant(name, desc, theme, phone, image, owner);

        // Use the newly created restaurant to create the address
        Address address = AddressDTOMapper.toEntity(addressGiven, restaurant);
        addressRepository.save(address);

        // Then, set the address to the restaurant
        restaurant.setAddress(address);
        Restaurant created = restaurantRepository.save(restaurant);
        return RestaurantDTOMapper.toDto(created);
    }


    public RestaurantDTO updateRestaurant(Long ownerId, Long restaurantId, String name, String desc, RestaurantTheme theme,
                                          String phone, String image, AddressDTO addressDTO) {
        return restaurantRepository.findById(restaurantId).map(restaurant -> {
            Address oldAddress = restaurant.getAddress();

            Optional.ofNullable(name).ifPresent(restaurant::setName);
            Optional.ofNullable(desc).ifPresent(restaurant::setDescription);
            Optional.ofNullable(theme).ifPresent(restaurant::setTheme);
            Optional.ofNullable(phone).ifPresent(restaurant::setPhone);
            Optional.ofNullable(image).ifPresent(restaurant::setImage);

            if (addressDTO != null) {
                Address newAddress = AddressDTOMapper.toEntity(addressDTO, restaurant);
                addressRepository.save(newAddress);
                restaurant.setAddress(newAddress);
            }

            Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

            if (oldAddress != null) {
                addressRepository.delete(oldAddress);
            }

            return RestaurantDTOMapper.toDto(updatedRestaurant);
        }).orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
    }


    public String deleteRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .map(restaurant -> {
                    restaurantRepository.delete(restaurant);
                    return "Deleted restaurant from with id " + restaurantId;
                }).orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
    }

}

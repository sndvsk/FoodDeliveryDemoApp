package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.service;

import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository.RestaurantRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.service.OwnerService;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantServiceImpl implements RestaurantService {

    private final OwnerService ownerService;
    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;

    public RestaurantServiceImpl(OwnerService ownerService, RestaurantRepository restaurantRepository, OwnerRepository ownerRepository) {
        this.ownerService = ownerService;
        this.restaurantRepository = restaurantRepository;
        this.ownerRepository = ownerRepository;
    }

    private void validateInputs() {

    }

    private void validateRequiredInputs() {

    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if (restaurants.isEmpty())
            throw new CustomNotFoundException("No restaurants in the database.");
        return restaurants;
    }

    public List<Restaurant> getRestaurantsByOwnerId(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .map(Owner::getRestaurants)
                .orElseThrow(() ->new CustomNotFoundException("Owner not found with id " + ownerId));
    }

    public List<Restaurant> getRestaurantsByTheme(String theme) {
        List<Restaurant> restaurants = restaurantRepository.findByTheme(theme);
        if (restaurants.isEmpty())
            throw new CustomNotFoundException("Restaurant not found with theme " + theme);
        return restaurants;
    }

    public Restaurant createRestaurant(String name, String desc, String theme, String address,
                                       String phone, String image) {
        Long ownerId = ownerService.getCurrentAccount();
        return createRestaurant(ownerId, name, desc, theme, address, phone, image);
    }


    public Restaurant createRestaurant(Long ownerId, String name, String desc, String theme, String address,
                                       String phone, String image) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with id " + ownerId));
        Restaurant restaurant = new Restaurant(name, desc, theme, address, phone, image, owner);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long restaurantId, String name, String desc, String theme, String address,
                                       String phone, String image) {
        Long ownerId = ownerService.getCurrentAccount();
        return restaurantRepository.findById(restaurantId).map(restaurant -> {
            if (name != null) {
                restaurant.setName(name);
            }
            if (desc != null) {
                restaurant.setDescription(desc);
            }
            if (theme != null) {
                restaurant.setTheme(theme);
            }
            if (address != null) {
                restaurant.setAddress(address);
            }
            if (phone != null) {
                restaurant.setPhone(phone);
            }
            if (image != null) {
                restaurant.setImage(image);
            }
            return restaurantRepository.save(restaurant);
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

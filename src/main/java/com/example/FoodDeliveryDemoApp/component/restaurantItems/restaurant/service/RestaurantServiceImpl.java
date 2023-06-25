package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.service;

import com.example.FoodDeliveryDemoApp.component.utils.OwnershipHelper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.RestaurantTheme;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto.RestaurantDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto.RestaurantDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository.RestaurantRepository;
import com.example.FoodDeliveryDemoApp.component.address.domain.Address;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTOMapper;
import com.example.FoodDeliveryDemoApp.component.address.repository.AddressRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.service.OwnerService;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RestaurantServiceImpl implements RestaurantService {

    private final OwnerService ownerService;
    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    private final AddressRepository addressRepository;

    public RestaurantServiceImpl(OwnerService ownerService,
                                 RestaurantRepository restaurantRepository,
                                 OwnerRepository ownerRepository,
                                 AddressRepository addressRepository) {
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
        List<RestaurantDTO> restaurants = restaurantRepository.findAll()
                .stream()
                .map(RestaurantDTOMapper::toDto)
                .collect(Collectors.toList());
        if (restaurants.isEmpty())
            throw new CustomNotFoundException("No restaurants in the database.");
        return restaurants;
    }

    public List<RestaurantDTO> getRestaurantsByOwnerId(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .map(owner -> owner.getRestaurants().stream()
                        .map(RestaurantDTOMapper::toDto)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with id " + ownerId));
    }

    public List<RestaurantDTO> getRestaurantsByTheme(String theme) {
        List<RestaurantDTO> restaurants = restaurantRepository.findByTheme(theme)
                .stream()
                .map(RestaurantDTOMapper::toDto)
                .collect(Collectors.toList());
        if (restaurants.isEmpty())
            throw new CustomNotFoundException("Restaurant not found with theme " + theme);
        return restaurants;
    }

    public RestaurantDTO createRestaurant(Long ownerId, String name, String desc, RestaurantTheme theme,
                                          String phone, String image, AddressDTO addressGiven) {
        return ownerRepository.findById(ownerId)
                .map(owner -> {
                    Restaurant restaurant = new Restaurant(name, desc, theme, phone, image, owner);
                    Address address = AddressDTOMapper.toEntity(addressGiven, restaurant);
                    restaurant.setAddress(address);
                    Restaurant created = restaurantRepository.save(restaurant);
                    return RestaurantDTOMapper.toDto(created);
                })
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with id " + ownerId));
    }

    public RestaurantDTO updateRestaurant(Long ownerId, Long restaurantId, String name, String desc,
                                          RestaurantTheme theme, String phone, String image, AddressDTO addressDTO) {
        return restaurantRepository.findById(restaurantId).map(restaurant -> {

            OwnershipHelper.validateOwner(ownerId, restaurant.getOwner().getId());

            Optional.ofNullable(name).ifPresent(restaurant::setName);
            Optional.ofNullable(desc).ifPresent(restaurant::setDescription);
            Optional.ofNullable(theme).ifPresent(restaurant::setTheme);
            Optional.ofNullable(phone).ifPresent(restaurant::setPhone);
            Optional.ofNullable(image).ifPresent(restaurant::setImage);

            if (addressDTO != null) {
                Address newAddress = AddressDTOMapper.toEntity(addressDTO, restaurant);
                restaurant.setAddress(newAddress);
            }

            Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

            return RestaurantDTOMapper.toDto(updatedRestaurant);
        }).orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
    }

    public String deleteRestaurant(Long restaurantId, Long ownerId) {
        return restaurantRepository.findById(restaurantId)
                .map(restaurant -> {
                    OwnershipHelper.validateOwner(ownerId, restaurant.getOwner().getId());
                    restaurantRepository.delete(restaurant);
                    return "Deleted restaurant from with id " + restaurantId;
                }).orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
    }

}

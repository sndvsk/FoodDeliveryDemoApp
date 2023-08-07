package com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.service;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.utils.AddressValidation;
import com.example.FoodDeliveryDemoApp.component.utils.OwnershipHelper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.Restaurant;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.domain.RestaurantTheme;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto.RestaurantDTO;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.dto.RestaurantDTOMapper;
import com.example.FoodDeliveryDemoApp.component.restaurantItems.restaurant.repository.RestaurantRepository;
import com.example.FoodDeliveryDemoApp.component.address.domain.Address;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTOMapper;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomAccessDeniedException;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
//import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Component
public class RestaurantServiceImpl implements RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;

    private final Executor threadPoolTaskExecutor;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 OwnerRepository ownerRepository,
                                 @Qualifier("threadPoolTaskExecutor")Executor threadPoolTaskExecutor) {
        this.restaurantRepository = restaurantRepository;
        this.ownerRepository = ownerRepository;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    private void validateInputs() {

    }

    private void validateRequiredInputs() {

    }

    @Transactional
    public List<RestaurantDTO> getAllRestaurants() {
        List<RestaurantDTO> restaurants = restaurantRepository.findAll()
                .stream()
                .map(RestaurantDTOMapper::toDto)
                .collect(Collectors.toList());
        if (restaurants.isEmpty())
            throw new CustomNotFoundException("No restaurants in the database.");
        return restaurants;
    }

    @Transactional
    public List<RestaurantDTO> getRestaurantsByOwnerId(Long ownerId) {
        Owner owner = ownerRepository.findOwnerByUserId(ownerId)
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with id " + ownerId));

        List<Restaurant> restaurants = owner.getRestaurants();

        if (restaurants.isEmpty()) {
            return Collections.emptyList();
        } else {
            return restaurants.stream()
                    .map(RestaurantDTOMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public List<RestaurantDTO> getRestaurantsByTheme(String theme) {
        RestaurantTheme parsedTheme = RestaurantTheme.fromString(theme);
        List<RestaurantDTO> restaurants = restaurantRepository.findByTheme(parsedTheme)
                .stream()
                .map(RestaurantDTOMapper::toDto)
                .collect(Collectors.toList());
        if (restaurants.isEmpty())
            throw new CustomNotFoundException("Restaurant not found with theme " + theme);
        return restaurants;
    }

    @Transactional
    public RestaurantDTO getRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
        return RestaurantDTOMapper.toDto(restaurant);
    }

    @Transactional
    public RestaurantDTO createRestaurant(Long ownerId, RestaurantDTO restaurantDTO) {

        String name = restaurantDTO.getName();
        String desc = restaurantDTO.getDescription();
        String theme = restaurantDTO.getTheme();
        String phone = restaurantDTO.getPhone();
        String image = "none"; // restaurantDTO.getImage();
        AddressDTO address_ = restaurantDTO.getAddress();
        // Validate the restaurantDTO
        if (name == null || name.trim().isEmpty()
                || desc == null || desc.trim().isEmpty()
                || theme == null
                || phone == null || phone.trim().isEmpty()
                //|| image == null || image.trim().isEmpty()
                || address_ == null) {
            throw new CustomBadRequestException("Incomplete restaurant data provided.");
        }

        AddressValidation.validateCreateAddress(address_);

        return ownerRepository.findOwnerByUserId(ownerId)
                .map(owner -> {
                    if (!owner.isApproved())
                        throw new CustomAccessDeniedException("You are not yet approved as an owner");

                    Restaurant restaurant = new Restaurant(
                            name, desc,
                            RestaurantTheme.valueOf(theme),
                            phone, image, owner);

                    Address address = AddressDTOMapper.toEntity(address_, restaurant);
                    restaurant.setAddress(address);
                    Restaurant created = restaurantRepository.save(restaurant);
                    return RestaurantDTOMapper.toDto(created);
                })
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with id " + ownerId));
    }


/*    public CompletableFuture<RestaurantDTO> createRestaurant(Long ownerId, String name, String desc, RestaurantTheme theme,
                                                             String phone, String image, AddressDTO addressGiven) {
        return CompletableFuture.supplyAsync(() -> ownerRepository.findById(ownerId)
                .map(owner -> {
                    Restaurant restaurant = new Restaurant(name, desc, theme, phone, image, owner);
                    Address address = AddressDTOMapper.toEntity(addressGiven, restaurant);
                    restaurant.setAddress(address);
                    Restaurant created = restaurantRepository.save(restaurant);
                    return RestaurantDTOMapper.toDto(created);
                })
                .orElseThrow(() ->
                        new CustomNotFoundException("Owner not found with id " + ownerId)), threadPoolTaskExecutor);
    }*/


    @Transactional
    public RestaurantDTO updateRestaurant(Long restaurantId, Long ownerId, RestaurantDTO restaurantDTO) {
        AddressDTO addressDTO = restaurantDTO.getAddress();
        AddressValidation.validateUpdateAddress(addressDTO);

        return restaurantRepository.findById(restaurantId).map(restaurant -> {

            OwnershipHelper.validateOwner(ownerId, restaurant.getOwner().getId());

            Optional.ofNullable(restaurantDTO.getName()).ifPresent(restaurant::setName);
            Optional.ofNullable(restaurantDTO.getDescription()).ifPresent(restaurant::setDescription);
            Optional.ofNullable(restaurantDTO.getTheme())
                    .map(RestaurantTheme::valueOf)
                    .ifPresent(restaurant::setTheme);
            Optional.ofNullable(restaurantDTO.getPhone()).ifPresent(restaurant::setPhone);
            //Optional.ofNullable(restaurantDTO.getPhone()).ifPresent(restaurant::setImage);
            restaurant.setImage("none");


            Address newAddress = AddressDTOMapper.toEntity(addressDTO, restaurant);
            if(!restaurant.getAddress().isAddressEqual(newAddress)){
                restaurant.getAddress().update(newAddress);
            }

            Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

            return RestaurantDTOMapper.toDto(updatedRestaurant);
        }).orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
    }

    @Transactional
    public String deleteRestaurant(Long restaurantId, Long ownerId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));
        OwnershipHelper.validateOwner(ownerId, restaurant.getOwner().getId());
        restaurantRepository.delete(restaurant);
        logger.info("Deleted restaurant from with id " + restaurantId);
        return "Deleted restaurant from with id " + restaurantId;
/*        return restaurantRepository.findById(restaurantId)
                .map(restaurant -> {
                    OwnershipHelper.validateOwner(ownerId, restaurant.getOwner().getId());
                    restaurantRepository.delete(restaurant);
                    return "Deleted restaurant from with id " + restaurantId;
                }).orElseThrow(() -> new CustomNotFoundException("Restaurant not found with id " + restaurantId));*/
    }



}

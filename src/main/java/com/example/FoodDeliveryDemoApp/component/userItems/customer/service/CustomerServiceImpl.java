package com.example.FoodDeliveryDemoApp.component.userItems.customer.service;

import com.example.FoodDeliveryDemoApp.component.address.domain.Address;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTO;
import com.example.FoodDeliveryDemoApp.component.address.dto.AddressDTOMapper;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import com.example.FoodDeliveryDemoApp.component.address.repository.AddressRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.repository.CustomerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.User;
import com.example.FoodDeliveryDemoApp.component.userItems.user.repository.UserRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               UserRepository userRepository,
                               AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public AddressDTO getAddress(Long userId) {
        User user = getUserById(userId);
        Address address = user.getCustomer().getAddress();
        //Address address = getAddressById(user.getId());
        if (address == null) {
            return AddressDTOMapper.toDto(new Address());
        } else
            return AddressDTOMapper.toDto(address);
    }

    @Transactional
    public AddressDTO addAddress(Long userId, AddressDTO addressDto) {
        User user = getUserById(userId);
        Customer customer = getCustomerById(user.getId());

        Address newAddress = buildAddress(addressDto);
        newAddress.setCustomer(customer);
        customer.setAddress(newAddress);

        user.setUpdatedAt(Instant.now());
        customer.setUser(user);
        customerRepository.save(customer);

        return AddressDTOMapper.toDto(newAddress);
    }

    public AddressDTO updateAddress(Long userId, AddressDTO addressDto) {
        User user = getUserById(userId);
        Customer customer = getCustomerById(user.getId());
        Address currentAddress = customer.getAddress();

        // Copy new values from DTO to address
        Address updatedAddress = buildAddress(addressDto);
        updatedAddress.setId(currentAddress.getId()); // retain original ID
        updatedAddress.setCustomer(customer);

        user.setUpdatedAt(Instant.now());
        customer.setAddress(updatedAddress);
        customerRepository.save(customer);

        return AddressDTOMapper.toDto(updatedAddress);
    }

    private Address buildAddress(AddressDTO addressDTO) {
        return AddressDTOMapper.toEntity(addressDTO, null);
    }

    private Customer getCustomerById(Long id) {
        return customerRepository.findByUserId(id)
                .orElseThrow(() -> new CustomNotFoundException("No customer with such id: " + id));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomNotFoundException("No user with such id: " + userId));
    }

    private Address getAddressById(Long id) {
        return addressRepository.findAddressByCustomerId(id)
                .orElseThrow(() -> new CustomNotFoundException("No addresses for this user"));
    }

}

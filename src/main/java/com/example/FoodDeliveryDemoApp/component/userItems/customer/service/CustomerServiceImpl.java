package com.example.FoodDeliveryDemoApp.component.userItems.customer.service;

import com.example.FoodDeliveryDemoApp.component.address.domain.Address;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import com.example.FoodDeliveryDemoApp.component.address.repository.AddressRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.repository.CustomerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.User;
import com.example.FoodDeliveryDemoApp.component.userItems.user.repository.UserRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

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

/*    public Customer updateUserInformation(String username, UserDetailsDTO updatedCustomer) {
        // Retrieve existing customer
        Customer existingCustomer = getCustomerByUsername(username);
        String existingEmail = existingCustomer.getEmail();
        String existingUsername = existingCustomer.getUsername();

        // Update only the non-null fields
        Optional.ofNullable(updatedCustomer.getFirstname()).ifPresent(existingCustomer::setFirstname);
        Optional.ofNullable(updatedCustomer.getLastname()).ifPresent(existingCustomer::setLastname);
        Optional.ofNullable(updatedCustomer.getEmail()).ifPresent(newEmail -> {
            if(emailExists(newEmail) && !newEmail.equals(existingEmail)) {
                throw new CustomBadRequestException(String.format("Email: %s is already in use", newEmail));
            }
            existingCustomer.setEmail(newEmail);
        });
        Optional.ofNullable(updatedCustomer.getUsername()).ifPresent(newUsername -> {
            if(usernameExists(newUsername) && !newUsername.equals(existingUsername)) {
                throw new CustomBadRequestException(String.format("Username: %s is already taken", newUsername));
            }
            existingCustomer.setUsername(newUsername);
        });
        Optional.ofNullable(updatedCustomer.getPassword()).ifPresent(
                password -> existingCustomer.setPassword(passwordEncoder.encode(password)));
        existingCustomer.setUpdatedAt(Instant.now());

        // Save and return updated customer
        return customerRepository.save(existingCustomer);
    }*/

    public Address addAddress(String username, Address address) {
        User user = getUserByUsername(username);
        Customer customer = getCustomerById(user.getId());
        address.setCustomer(customer);
        addressRepository.save(address);
        Address savedAddress = getAddressByUsername(user.getId());
        customer.setAddress(savedAddress);
        customerRepository.save(customer);
        return address;
    }

    public Address getAddress(String username) {
        User user = getUserByUsername(username);
        return getAddressByUsername(user.getId());
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("No customer with such id: " + id));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException("No user with such username: " + username));
    }

    public Address getAddressByUsername(Long id) {
        return addressRepository.findAddressByCustomer_Id(id)
                .orElseThrow(() -> new CustomNotFoundException("No addresses for this user"));
    }

}

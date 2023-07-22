package com.example.FoodDeliveryDemoApp.component.userItems.user.service;

import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.User;
import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.UserDetailsImpl;
import com.example.FoodDeliveryDemoApp.component.userItems.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

/*    private final OwnerRepository ownerRepository;
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;*/

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

/*    public UserDetailsServiceImpl(OwnerRepository ownerRepository, AdminRepository adminRepository, CustomerRepository customerRepository) {
        this.ownerRepository = ownerRepository;
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }*/

/*    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        Optional<Owner> ownerOptional = ownerRepository.findByUsername(username);
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);

        if(ownerOptional.isPresent()) {
            user = ownerOptional.get();
        } else if(adminOptional.isPresent()) {
            user = adminOptional.get();
        } else if(customerOptional.isPresent()) {
            user = customerOptional.get();
        } else {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }

        return UserDetailsImpl.build(user);
    }

    @Transactional
    public UserDetails loadUserById(String userId) throws UsernameNotFoundException {
        User user;

        Long id = Long.parseLong(userId);

        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        Optional<Admin> adminOptional = adminRepository.findById(id);
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if(ownerOptional.isPresent()) {
            user = ownerOptional.get();
        } else if(adminOptional.isPresent()) {
            user = adminOptional.get();
        } else if(customerOptional.isPresent()) {
            user = customerOptional.get();
        } else {
            throw new UsernameNotFoundException("User Not Found with ID: " + id);
        }

        return UserDetailsImpl.build(user);
    }*/

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    @Transactional
    public UserDetails loadUserById(String userId) throws UsernameNotFoundException {
        long id;
        try {
            id = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid User ID: " + userId, e);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with ID: " + id));

        return UserDetailsImpl.build(user);
    }

}


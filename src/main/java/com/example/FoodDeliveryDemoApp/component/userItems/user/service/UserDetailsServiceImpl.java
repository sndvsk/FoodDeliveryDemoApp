package com.example.FoodDeliveryDemoApp.component.userItems.user.service;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.domain.Admin;
import com.example.FoodDeliveryDemoApp.component.userItems.admin.repository.AdminRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.repository.CustomerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.User;
import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final OwnerRepository ownerRepository;
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    public UserDetailsServiceImpl(OwnerRepository ownerRepository, AdminRepository adminRepository, CustomerRepository customerRepository) {
        this.ownerRepository = ownerRepository;
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }

    @Override
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

}


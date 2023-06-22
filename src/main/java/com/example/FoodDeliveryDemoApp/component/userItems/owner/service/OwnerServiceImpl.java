package com.example.FoodDeliveryDemoApp.component.userItems.owner.service;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.User;
import com.example.FoodDeliveryDemoApp.component.userItems.user.repository.UserRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository,
                            UserRepository userRepository) {
        this.ownerRepository = ownerRepository;
        this.userRepository = userRepository;
    }

    public Long getCurrentAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            User user = getOwnerByUsername(username);
            if (user != null) {
                return user.getId();
            }
            throw new UsernameNotFoundException("Owner not found with username: " + username);
        }
        throw new IllegalArgumentException("Invalid principal type");
    }

    public User getOwnerByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException("No user with such username: " + username));
    }

    public Long getIdByUsername(String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException("No user with such username: " + username));
        return owner.getId();
    }

}

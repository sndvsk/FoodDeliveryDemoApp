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

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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


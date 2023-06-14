package com.example.FoodDeliveryDemoApp.component.userItems.owner.service;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.domain.Admin;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.RegisterRequest;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@Component
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    private final PasswordEncoder passwordEncoder;

    public OwnerServiceImpl(OwnerRepository ownerRepository, PasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long getCurrentAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            Owner owner = ownerRepository.findByUsername(username)
                    .orElseThrow(() -> new CustomBadRequestException("No such 'OWNER' user " + username));
            if (owner != null) {
                return owner.getId();
            }
            throw new UsernameNotFoundException("Owner not found with username: " + username);
        }
        throw new IllegalArgumentException("Invalid principal type");
    }

    public Owner registerOwner(RegisterRequest request) {
        if (ownerRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username already exists");
        }

        // Create new Owner object and populate its fields from the request
        Owner owner = Owner.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(request.getRole())
                .createdAt(Instant.now())
                // Set other fields if necessary
                .build();

        owner.setPassword(passwordEncoder.encode(request.getPassword()));
        Owner savedOwner = ownerRepository.save(owner);

        return savedOwner;
    }


    public Optional<Owner> getOwnerByUsername(String username) {
        return ownerRepository.findByUsername(username);
                //.orElseThrow(() -> new CustomNotFoundException("No user with such username: " + username));
    }

}

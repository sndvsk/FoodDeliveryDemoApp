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

    public OwnerServiceImpl(OwnerRepository ownerRepository, UserRepository userRepository) {
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

/*    public Owner updateUserInformation(String username, UserDetailsDTO updatedOwner) {
        // Retrieve existing customer
        Owner existingOwner = getOwnerByUsername(username);
        String existingEmail = existingOwner.getEmail();
        String existingUsername = existingOwner.getUsername();

        // Update only the non-null fields
        Optional.ofNullable(updatedOwner.getFirstname()).ifPresent(existingOwner::setFirstname);
        Optional.ofNullable(updatedOwner.getLastname()).ifPresent(existingOwner::setLastname);
        Optional.ofNullable(updatedOwner.getEmail()).ifPresent(newEmail -> {
            if(emailExists(newEmail) && !newEmail.equals(existingEmail)) {
                throw new CustomBadRequestException(String.format("Email: %s is already in use", newEmail));
            }
            existingOwner.setEmail(newEmail);
        });
        Optional.ofNullable(updatedOwner.getUsername()).ifPresent(newUsername -> {
            if(usernameExists(newUsername) && !newUsername.equals(existingUsername)) {
                throw new CustomBadRequestException(String.format("Username: %s is already taken", newUsername));
            }
            existingOwner.setUsername(newUsername);
        });
        Optional.ofNullable(updatedOwner.getPassword()).ifPresent(
                password -> existingOwner.setPassword(passwordEncoder.encode(password)));
        existingOwner.setUpdatedAt(Instant.now());

        // Save and return updated customer
        return ownerRepository.save(existingOwner);
    }

    public boolean usernameExists(String username) {
        return ownerRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return ownerRepository.existsByEmail(email);
    }*/

/*    public Owner registerOwner(RegisterRequest request) {
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
    }*/

}

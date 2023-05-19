package com.example.FoodDeliveryDemoApp.component.userItems.owner.service;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public OwnerServiceImpl(OwnerRepository ownerRepository, BCryptPasswordEncoder passwordEncoder) {
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

    public Owner registerOwner(Owner owner) {
        if (ownerRepository.findByUsername(owner.getUsername()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username already exists");
        }

        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        Owner savedOwner = ownerRepository.save(owner);

        return savedOwner;
    }

}

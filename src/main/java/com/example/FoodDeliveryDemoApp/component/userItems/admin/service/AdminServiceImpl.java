package com.example.FoodDeliveryDemoApp.component.userItems.admin.service;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.domain.Admin;
import com.example.FoodDeliveryDemoApp.component.userItems.admin.repository.AdminRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.repository.CustomerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final OwnerRepository ownerRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository,
                            OwnerRepository ownerRepository,
                            CustomerRepository customerRepository,
                            PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.ownerRepository = ownerRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin registerAdmin(Admin admin) {
        if (adminRepository.findByUsername(admin.getUsername()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username already exists");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin savedAdmin = adminRepository.save(admin);

        return savedAdmin;
    }

    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
                //.orElseThrow(() -> new CustomNotFoundException("No user with such username: " + username));
    }

    public void approveOwner(Long ownerId) {
        Owner owner = getOwnerById(ownerId);
        owner.setApproved(true);
        // Save the updated owner entity
        ownerRepository.save(owner);
    }

    public void rejectOwner(Long ownerId) {
        Owner owner = getOwnerById(ownerId);
        // Delete the owner entity
        ownerRepository.delete(owner);
    }

    private Owner getOwnerById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with ID: " + ownerId));
    }

    public List<Owner> getOwnersWithApprovalStatus(boolean approved) {
        return ownerRepository.findByApproved(approved)
                .orElseThrow(() -> new CustomNotFoundException("No unapproved owners"));
    }

}

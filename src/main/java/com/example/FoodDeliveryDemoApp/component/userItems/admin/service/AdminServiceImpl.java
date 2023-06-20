package com.example.FoodDeliveryDemoApp.component.userItems.admin.service;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.repository.AdminRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.repository.CustomerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.user.repository.UserRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final CustomerRepository customerRepository;

    public AdminServiceImpl(AdminRepository adminRepository,
                            UserRepository userRepository,
                            OwnerRepository ownerRepository,
                            CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.customerRepository = customerRepository;
    }

    /*public Admin updateUserInformation(String username, UserDetailsDTO updatedAdmin) {
        // Retrieve existing customer
        Admin existingAdmin = getAdminByUsername(username);
        String existingEmail = existingAdmin.getEmail();
        String existingUsername = existingAdmin.getUsername();

        // Update only the non-null fields
        Optional.ofNullable(updatedAdmin.getFirstname()).ifPresent(existingAdmin::setFirstname);
        Optional.ofNullable(updatedAdmin.getLastname()).ifPresent(existingAdmin::setLastname);
        Optional.ofNullable(updatedAdmin.getEmail()).ifPresent(newEmail -> {
            if(emailExists(newEmail) && !newEmail.equals(existingEmail)) {
                throw new CustomBadRequestException(String.format("Email: %s is already in use", newEmail));
            }
            existingAdmin.setEmail(newEmail);
        });
        Optional.ofNullable(updatedAdmin.getUsername()).ifPresent(newUsername -> {
            if(usernameExists(newUsername) && !newUsername.equals(existingUsername)) {
                throw new CustomBadRequestException(String.format("Username: %s is already taken", newUsername));
            }
            existingAdmin.setUsername(newUsername);
        });
        Optional.ofNullable(updatedAdmin.getPassword()).ifPresent(
                password -> existingAdmin.setPassword(passwordEncoder.encode(password)));
        existingAdmin.setUpdatedAt(Instant.now());

        // Save and return updated customer
        return adminRepository.save(existingAdmin);
    }*/


/*    public boolean emailExists(String email) {
        return customerRepository.existsByEmail(email);
    }*/

/*    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new CustomNotFoundException("No user with such username: " + username));
    }*/

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

/*    public boolean usernameExists(String username) {
        return customerRepository.existsByUsername(username);
    }*/

/*    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username)
            .orElseThrow(() -> new CustomNotFoundException("No user with such username: " + username));
    }*/

}

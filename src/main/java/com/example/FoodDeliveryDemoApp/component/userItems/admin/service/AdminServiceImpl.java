package com.example.FoodDeliveryDemoApp.component.userItems.admin.service;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.repository.AdminRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.repository.CustomerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.dto.OwnerDTO;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.dto.OwnerDTOMapper;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.user.repository.UserRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public String approveOwner(Long ownerId) {
        Owner owner = getOwnerById(ownerId);
        owner.setApproved(true);
        // Save the updated owner entity
        ownerRepository.save(owner);
        return String.format("Owner with username: ´%s´, id: ´%s´ is approved.", owner.getUser().getUsername(), owner.getId());
    }

    public String rejectOwner(Long ownerId) {
        Owner owner = getOwnerById(ownerId);
        // Delete the owner entity
        //ownerRepository.delete(owner);
        return String.format("Owner with username: ´%s´, id: ´%s´ is rejected.", owner.getUser().getUsername(), owner.getId());
    }

    private Owner getOwnerById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new CustomNotFoundException("Owner not found with ID: " + ownerId));
    }

    @Transactional
    public List<OwnerDTO> getOwnersWithApprovalStatus(boolean approved) {
        Optional<List<Owner>> optionalOwners = ownerRepository.findByApproved(approved);

        return optionalOwners.isPresent()
                ? OwnerDTOMapper.toDtoList(optionalOwners.get())
                : Collections.emptyList();
    }

    @Transactional
    public List<OwnerDTO> getOwners() {
        List<Owner> owners = ownerRepository.findAll();
        return !owners.isEmpty()
                ? OwnerDTOMapper.toDtoList(owners)
                : Collections.emptyList();
    }

}

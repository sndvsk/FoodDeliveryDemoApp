package com.example.FoodDeliveryDemoApp.component.userItems.admin.service;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.domain.Admin;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    Admin registerAdmin(Admin admin);

    Optional<Admin> getAdminByUsername(String username);

    void approveOwner(Long ownerId);

    void rejectOwner(Long ownerId);

    List<Owner> getOwnersWithApprovalStatus(boolean approved);

}

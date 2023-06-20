package com.example.FoodDeliveryDemoApp.component.userItems.admin.service;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;

import java.util.List;

public interface AdminService {

    //Admin getAdminByUsername(String username);

    void approveOwner(Long ownerId);

    void rejectOwner(Long ownerId);

    List<Owner> getOwnersWithApprovalStatus(boolean approved);

    //Admin updateUserInformation(String username, UserDetailsDTO admin);
}

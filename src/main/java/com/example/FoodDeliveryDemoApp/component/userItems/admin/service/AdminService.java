package com.example.FoodDeliveryDemoApp.component.userItems.admin.service;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;

import java.util.List;

public interface AdminService {

    //Admin getAdminByUsername(String username);

    String approveOwner(Long ownerId);

    String rejectOwner(Long ownerId);

    List<Owner> getOwnersWithApprovalStatus(boolean approved);

    //Admin updateUserInformation(String username, UserDetailsDTO admin);
}

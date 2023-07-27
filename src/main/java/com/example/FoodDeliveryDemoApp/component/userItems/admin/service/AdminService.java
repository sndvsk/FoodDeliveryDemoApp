package com.example.FoodDeliveryDemoApp.component.userItems.admin.service;

import com.example.FoodDeliveryDemoApp.component.userItems.owner.dto.OwnerDTO;

import java.util.List;

public interface AdminService {


    String approveOwner(Long ownerId);

    String rejectOwner(Long ownerId);

    List<OwnerDTO> getOwnersWithApprovalStatus(boolean approved);

    List<OwnerDTO> getOwners();
}

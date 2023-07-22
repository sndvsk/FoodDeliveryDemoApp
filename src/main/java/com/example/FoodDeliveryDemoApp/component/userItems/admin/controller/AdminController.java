package com.example.FoodDeliveryDemoApp.component.userItems.admin.controller;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.service.AdminService;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.dto.OwnerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/admins")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/approve-owner/{ownerId}")
    public ResponseEntity<String> approveOwner(@PathVariable Long ownerId) {
        String response = adminService.approveOwner(ownerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reject-owner/{ownerId}")
    public ResponseEntity<String> rejectOwner(@PathVariable Long ownerId) {
        String response = adminService.rejectOwner(ownerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/owners/approved-false")
    public ResponseEntity<List<OwnerDTO>> getOwnersWithApprovalStatusFalse() {
        List<OwnerDTO> owners = adminService.getOwnersWithApprovalStatus(false);
        return ResponseEntity.ok(owners);
    }

}

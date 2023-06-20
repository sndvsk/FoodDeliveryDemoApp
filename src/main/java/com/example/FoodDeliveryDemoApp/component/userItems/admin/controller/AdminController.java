package com.example.FoodDeliveryDemoApp.component.userItems.admin.controller;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.service.AdminService;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
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
    public ResponseEntity<Void> approveOwner(@PathVariable Long ownerId) {
        adminService.approveOwner(ownerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject-owner/{ownerId}")
    public ResponseEntity<Void> rejectOwner(@PathVariable Long ownerId) {
        adminService.rejectOwner(ownerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/owners/approved-false")
    public ResponseEntity<List<Owner>> getOwnersWithApprovalStatusFalse() {
        List<Owner> owners = adminService.getOwnersWithApprovalStatus(false);
        return ResponseEntity.ok(owners);
    }

}

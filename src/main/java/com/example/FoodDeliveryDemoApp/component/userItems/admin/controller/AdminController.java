package com.example.FoodDeliveryDemoApp.component.userItems.admin.controller;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.domain.Admin;
import com.example.FoodDeliveryDemoApp.component.userItems.admin.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {

        Admin savedAdmin = adminService.registerAdmin(admin);

        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

}

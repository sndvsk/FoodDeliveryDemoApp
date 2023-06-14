package com.example.FoodDeliveryDemoApp.security.service;

import com.example.FoodDeliveryDemoApp.component.userItems.Role;
import com.example.FoodDeliveryDemoApp.component.userItems.admin.domain.Admin;
import com.example.FoodDeliveryDemoApp.component.userItems.admin.repository.AdminRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AdminByDefaultService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminByDefaultService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        createDefaultAdminIfNotExist();
    }

    private void createDefaultAdminIfNotExist() {
        if (adminRepository.count() == 0) {
            // Create the default admin user
            Admin defaultAdmin = Admin.builder()
                    .username("admin")
                    .firstname("Default")
                    .lastname("Admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("root"))
                    .role(Role.ADMIN)
                    .createdAt(Instant.now())
                    .level(3L)
                    .build();

            adminRepository.save(defaultAdmin);
        }
    }

}

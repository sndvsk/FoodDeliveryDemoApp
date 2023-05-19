package com.example.FoodDeliveryDemoApp.component.userItems.admin.service;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.domain.Admin;
import com.example.FoodDeliveryDemoApp.component.userItems.admin.repository.AdminRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin registerAdmin(Admin admin) {
        if (adminRepository.findByUsername(admin.getUsername()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username already exists");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin savedAdmin = adminRepository.save(admin);

        return savedAdmin;
    }
}

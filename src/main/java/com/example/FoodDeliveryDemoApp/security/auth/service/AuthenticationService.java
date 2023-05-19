package com.example.FoodDeliveryDemoApp.security.auth.service;

import com.example.FoodDeliveryDemoApp.component.userItems.admin.domain.Admin;
import com.example.FoodDeliveryDemoApp.component.userItems.admin.repository.AdminRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.domain.Customer;
import com.example.FoodDeliveryDemoApp.component.userItems.customer.repository.CustomerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.domain.Owner;
import com.example.FoodDeliveryDemoApp.component.userItems.owner.repository.OwnerRepository;
import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.User;
import com.example.FoodDeliveryDemoApp.security.auth.dto.AuthenticationRequest;
import com.example.FoodDeliveryDemoApp.security.auth.dto.AuthenticationResponse;
import com.example.FoodDeliveryDemoApp.security.auth.dto.RegisterRequest;
import com.example.FoodDeliveryDemoApp.security.jwt.JwtService;
import com.example.FoodDeliveryDemoApp.security.token.Token;
import com.example.FoodDeliveryDemoApp.security.token.TokenType;
import com.example.FoodDeliveryDemoApp.security.token.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class AuthenticationService {

    private final AdminRepository adminRepository;
    private final OwnerRepository ownerRepository;
    private final CustomerRepository customerRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(AdminRepository adminRepository, OwnerRepository ownerRepository,
                                 CustomerRepository customerRepository, TokenRepository tokenRepository,
                                 PasswordEncoder passwordEncoder, JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.adminRepository = adminRepository;
        this.ownerRepository = ownerRepository;
        this.customerRepository = customerRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private User saveUser(User user) {
        switch (user.getRole()) {
            case ADMIN:
                return adminRepository.save((Admin) user);
            case OWNER:
                return ownerRepository.save((Owner) user);
            case CUSTOMER:
                return customerRepository.save((Customer) user);
            default:
                throw new IllegalArgumentException("Invalid user role");
        }
    }

    private Optional<User> findByEmail(String email) {
        var admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            return Optional.of(admin.get());
        }

        var owner = ownerRepository.findByEmail(email);
        if (owner.isPresent()) {
            return Optional.of(owner.get());
        }

        var customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            return Optional.of(customer.get());
        }

        return Optional.empty();
    }

    private Optional<User> findByUsername(String username) {
        var admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return Optional.of(admin.get());
        }

        var owner = ownerRepository.findByUsername(username);
        if (owner.isPresent()) {
            return Optional.of(owner.get());
        }

        var customer = customerRepository.findByUsername(username);
        if (customer.isPresent()) {
            return Optional.of(customer.get());
        }

        return Optional.empty();
    }

    public AuthenticationResponse register(RegisterRequest request) {
/*        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();*/
        User savedUser;
        User user;
        switch(request.getRole()) {
            case ADMIN:
                user = Admin.builder()
                        .username(request.getUsername())
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .level(3L)
                        .build();
                savedUser = adminRepository.save((Admin) user);
                break;
            case OWNER:
                user = Owner.builder()
                        .username(request.getUsername())
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .build();
                savedUser = ownerRepository.save((Owner) user);
                break;
            case CUSTOMER:
                user = Customer.builder()
                        .username(request.getUsername())
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .build();
                savedUser = customerRepository.save((Customer) user);
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + request.getRole());
        }

        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            // Log the exception and return an appropriate response
            System.out.println("Error:" + e);
        }

        //SecurityContextHolder.getContext().setAuthentication(authentication);
        var user = this.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        // Check the type of user and set the appropriate field
        if (user instanceof Admin) {
            token.setAdmin((Admin) user);
        } else if (user instanceof Owner) {
            token.setOwner((Owner) user);
        } else if (user instanceof Customer) {
            token.setCustomer((Customer) user);
        } else {
            throw new IllegalArgumentException("Unsupported user type: " + user.getClass());
        }

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens;

        if (user instanceof Admin) {
            validUserTokens = tokenRepository.findAllValidTokenByAdmin(user.getId());
        } else if (user instanceof Owner) {
            validUserTokens = tokenRepository.findAllValidTokenByOwner(user.getId());
        } else if (user instanceof Customer) {
            validUserTokens = tokenRepository.findAllValidTokenByCustomer(user.getId());
        } else {
            throw new IllegalArgumentException("Unsupported user type: " + user.getClass());
        }

        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUsername(refreshToken);
        if (userName != null) {
            var user = this.findByUsername(userName)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }


}

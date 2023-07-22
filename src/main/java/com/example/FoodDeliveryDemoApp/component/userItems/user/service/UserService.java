package com.example.FoodDeliveryDemoApp.component.userItems.user.service;

import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.UserDTO;
import com.example.FoodDeliveryDemoApp.component.userItems.user.dto.UserDTOMapper;
import com.example.FoodDeliveryDemoApp.component.userItems.user.domain.User;
import com.example.FoodDeliveryDemoApp.component.userItems.user.repository.UserRepository;
import com.example.FoodDeliveryDemoApp.component.address.repository.AddressRepository;
import com.example.FoodDeliveryDemoApp.exception.CustomBadRequestException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AddressRepository addressRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDTO updateUserInformation(String username, UserDTO updatedUser) {
        User existingUser = getUserByUsername(username);
        String existingEmail = existingUser.getEmail();
        String existingUsername = existingUser.getUsername();

        Optional.ofNullable(updatedUser.getFirstname()).ifPresent(existingUser::setFirstname);
        Optional.ofNullable(updatedUser.getLastname()).ifPresent(existingUser::setLastname);
        Optional.ofNullable(updatedUser.getEmail()).ifPresent(newEmail -> {
            if (emailExists(newEmail) && !newEmail.equals(existingEmail)) {
                throw new CustomBadRequestException(String.format("Email: %s is already in use", newEmail));
            }
            existingUser.setEmail(newEmail);
        });
        Optional.ofNullable(updatedUser.getUsername()).ifPresent(newUsername -> {
            if (usernameExists(newUsername) && !newUsername.equals(existingUsername)) {
                throw new CustomBadRequestException(String.format("Username: %s is already taken", newUsername));
            }
            existingUser.setUsername(newUsername);
        });
        Optional.ofNullable(updatedUser.getPassword()).ifPresent(
                password -> existingUser.setPassword(passwordEncoder.encode(password)));
        existingUser.setUpdatedAt(Instant.now());

        // Update the username in the associated user entity
        if (!existingUsername.equals(updatedUser.getUsername())) {
            updateUserUsername(existingUsername, updatedUser.getUsername());
        }

        return UserDTOMapper.toDto(saveUser(existingUser));
    }

    public UserDTO getUserInformation(String username) {
        return UserDTOMapper.toDto(getUserByUsername(username));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        User user = findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user with username: " + username));
        return user;
    }

    private void updateUserUsername(String existingUsername, String newUsername) {
        findUserByUsername(existingUsername).ifPresent(user -> {
            user.setUsername(newUsername);
            saveUser(user);
        });
    }

    private Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

}
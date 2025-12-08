package com.abhi.skylink.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abhi.skylink.dto.UserRegistrationRequest;
import com.abhi.skylink.dto.UserResponse;
import com.abhi.skylink.model.User;
import com.abhi.skylink.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register new user
    @Transactional
    public UserResponse registerUser(UserRegistrationRequest req) {

        // 1. Check if email already exists
        User existing = userRepository.findByEmail(req.getEmail());
        if (existing != null) {
            throw new RuntimeException("User already exists with this email!");
        }

        // 2. Create new User object
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword()); // later: hash password
        user.setPhone(req.getPhone());
        user.setRole("CUSTOMER");

        // 3. Save user to DB
        userRepository.save(user);

        // 4. Fetch saved user again to get ID
        User savedUser = userRepository.findByEmail(req.getEmail());

        // 5. Convert User -> UserResponse
        return convertToResponse(savedUser);
    }

    // Get user by ID
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        return convertToResponse(user);
    }

    // Utility: convert Entity -> DTO
    private UserResponse convertToResponse(User user) {
        UserResponse resp = new UserResponse();
        resp.setId(user.getId());
        resp.setName(user.getName());
        resp.setEmail(user.getEmail());
        resp.setPhone(user.getPhone());
        resp.setRole(user.getRole());
        return resp;
    }
}

package com.abhi.skylink.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abhi.skylink.dto.UserRegistrationRequest;
import com.abhi.skylink.dto.UserResponse;
import com.abhi.skylink.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ================================
    // 1. REGISTER USER (POST)
    // ================================
    @PostMapping
    public ResponseEntity<UserResponse> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {

        UserResponse created = userService.registerUser(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ================================
    // 2. GET USER BY ID (GET)
    // ================================
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
}

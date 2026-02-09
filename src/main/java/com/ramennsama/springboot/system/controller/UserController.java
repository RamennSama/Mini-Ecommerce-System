package com.ramennsama.springboot.system.controller;

import com.ramennsama.springboot.system.dto.request.UserRequest;
import com.ramennsama.springboot.system.dto.response.UserResponse;
import com.ramennsama.springboot.system.entity.User;
import com.ramennsama.springboot.system.mapper.UserMapper;
import com.ramennsama.springboot.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users in E-Commerce system")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @Operation(
            summary = "Get all users",
            description = "Returns a complete list of all users in the system"
    )
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID",
            description = "Returns detailed information of a user based on ID"
    )
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID of the user to find", required = true)
            @PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }

    @PostMapping
    @Operation(
            summary = "Create new user",
            description = "Creates a new user in the system"
    )
    public ResponseEntity<UserResponse> createUser(
            @Parameter(description = "User information to create", required = true)
            @RequestBody UserRequest userRequest) {
        User savedUser = userService.save(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserResponse(savedUser));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update user information",
            description = "Updates information of an existing user"
    )
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "ID of the user to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated information", required = true)
            @RequestBody UserRequest userRequest) {
        User updatedUser = userService.update(id, userRequest);
        return ResponseEntity.ok(userMapper.toUserResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user",
            description = "Deletes a user from the system"
    )
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true)
            @PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

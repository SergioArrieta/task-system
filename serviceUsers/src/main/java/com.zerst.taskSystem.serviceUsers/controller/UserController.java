package com.zerst.taskSystem.serviceUsers.controller;


import com.zerst.taskSystem.serviceUsers.services.UserDTO;
import com.zerst.taskSystem.serviceUsers.services.UserServices;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userServices.getUserById(id));
    }

    @GetMapping()
    public ResponseEntity<@NonNull List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userServices.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<@NonNull UserDTO> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userServices.createUser(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<@NonNull UserDTO> updateUser(@PathVariable Long id, @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userServices.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userServices.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

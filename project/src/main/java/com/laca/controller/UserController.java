package com.laca.controller;


import com.laca.entity.Interfaces.IConstructUser;
import com.laca.entity.PackageUnitAbstract.Users;
import com.laca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transporters")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<IConstructUser> getAllUsers() {
        List<IConstructUser> users = userService.getAllUsers();
        return users;
    }

    @PostMapping
    public Users saveUser(@RequestBody Users users) {
        return userService.saveUser(users);
    }

    @PutMapping("/{transporterId}")
    public ResponseEntity<?> updateTransporter(
            @PathVariable Long userId,
            @RequestBody Users updatedUser) {
        try {
            Users updated = userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating transporter: " + e.getMessage());
        }
    }

    @GetMapping("/{transporterId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            Users transporter = userService.getUserById(userId);
            return ResponseEntity.ok(transporter);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transporter not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/{transporterId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            boolean isDeleted = userService.deleteUser(userId);
            Users transporter= new Users();
            transporter.setId(userId);
            if (isDeleted) {
                return ResponseEntity.ok(transporter);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userId);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting transporter: " + e.getMessage());
        }

    }
}
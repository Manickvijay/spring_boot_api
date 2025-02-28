package com.example.demo.Controller;


import com.example.demo.DTO.AdminLoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@RequestBody AdminLoginRequest request) {
        // Hardcoded admin credentials for demonstration
        String adminUsername = "admin@gmail";
        String adminPassword = "admin123";
        // Check if the provided credentials match
        if (adminUsername.equals(request.getUsername()) && adminPassword.equals(request.getPassword())) {
            return ResponseEntity.ok("Admin login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
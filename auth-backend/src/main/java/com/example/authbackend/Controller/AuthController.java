package com.example.authbackend.controller;
import java.util.Optional;
import com.example.authbackend.dto.RegisterRequest;
import com.example.authbackend.model.User;
import com.example.authbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // allow your React dev server
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest req) {
        Map<String, String> resp = new HashMap<>();

        // basic server-side checks
        if (req.getFirstname() == null || req.getFirstname().isBlank() ||
            req.getLastname() == null || req.getLastname().isBlank() ||
            req.getUsername() == null || req.getUsername().isBlank() ||
            req.getEmail() == null || req.getEmail().isBlank() ||
            req.getPassword() == null || req.getPassword().isBlank()) {

            resp.put("message", "Please fill all fields");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByUsername(req.getUsername())) {
            resp.put("message", "Username already exists");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(req.getEmail())) {
            resp.put("message", "Email already registered");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        // hash the password
        String hashed = passwordEncoder.encode(req.getPassword());

        User user = new User(req.getFirstname(), req.getLastname(), req.getUsername(), req.getEmail(), hashed);
        userRepository.save(user);

        resp.put("message", "User registered successfully");
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> req) {
    Map<String, String> resp = new HashMap<>();
    
    String email = req.get("email");
    String password = req.get("password");

    if (email == null || password == null) {
        resp.put("message", "Email and password required");
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    Optional<User> userOpt = userRepository.findByEmail(email);
    if (userOpt.isEmpty()) {
        resp.put("message", "User not found");
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    User user = userOpt.get();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    if (!passwordEncoder.matches(password, user.getPassword())) {
        resp.put("message", "Incorrect password");
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    resp.put("message", "Login successful");
    // You can also send back user info if needed
    return new ResponseEntity<>(resp, HttpStatus.OK);
}

}

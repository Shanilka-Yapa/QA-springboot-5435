package com.example.authbackend.controller;

import com.example.authbackend.repository.UserRepository;
import com.example.authbackend.dto.RegisterRequest;
import com.example.authbackend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ==========================
    // Feature 1: Register
    // ==========================
    /*@Test
    void testRegisterSuccess() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setUsername("test");
        request.setEmail("test@example.com");
        request.setPassword("12345");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.existsByUsername("test")).thenReturn(false);

        // Act
        ResponseEntity<Map<String, String>> response = authController.register(request);

        // RED-GREEN-REFACTOR toggle
        String expectedMessage = "User registered successfully"; // GREEN
        //String expectedMessage = "Wrong message"; // RED

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(expectedMessage, response.getBody().get("message"));
    }

    // ==========================
    // Feature 2: Login
    // ==========================
    @Test
    void testLoginSuccess() {
        // Arrange
        User mockUser = new User("John", "Doe", "test", "test@example.com", passwordEncoder.encode("12345"));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        // Act
        ResponseEntity<Map<String, String>> response = authController.login(Map.of(
                "email", "test@example.com",
                "password", "12345"
        ));

        // RED-GREEN-REFACTOR toggle
        String expectedMessage = "Login successful"; // GREEN
        //String expectedMessage = "Wrong message"; // RED

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedMessage, response.getBody().get("message"));
    }*/


    //Refactored code
    // ==========================
    // Helper Methods for Refactor
    // ==========================
    private RegisterRequest createRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setUsername("test");
        request.setEmail("test@example.com");
        request.setPassword("12345");
        return request;
    }

    private User createMockUser() {
        return new User("John", "Doe", "test", "test@example.com", passwordEncoder.encode("12345"));
    }

    // ==========================
    // Feature 1: Register
    // ==========================
    @Test
    void testRegisterSuccess() {
        // Arrange
        RegisterRequest request = createRegisterRequest();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);

        // Act
        ResponseEntity<Map<String, String>> response = authController.register(request);

        // Assert
        String expectedMessage = "User registered successfully";
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(expectedMessage, response.getBody().get("message"));
    }

    // ==========================
    // Feature 2: Login
    // ==========================
    @Test
    void testLoginSuccess() {
        // Arrange
        User mockUser = createMockUser();
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        // Act
        ResponseEntity<Map<String, String>> response = authController.login(Map.of(
                "email", "test@example.com",
                "password", "12345"
        ));

        // Assert
        String expectedMessage = "Login successful";
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedMessage, response.getBody().get("message"));
    }


}

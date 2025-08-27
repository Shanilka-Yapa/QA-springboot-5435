package com.example.authbackend.controller;

import com.example.authbackend.model.User;
import com.example.authbackend.repository.UserRepository;
import com.example.authbackend.controller.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@example.com");
        user.setPassword("12345");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.existsByUsername("test")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<Map<String, String>> response = authController.register(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", response.getBody().get("message"));
    }
}

package com.example.authbackend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.authbackend.model.User;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
}

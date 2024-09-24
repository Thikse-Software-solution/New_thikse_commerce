package com.example.admin.Repository;

import com.example.admin.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Find user by email
    Optional<User> findByEmail(String email);

    // Find user by mobile number
    Optional<User> findByMobileNumber(String mobileNumber);

    // Get all users
    List<User> findAll();

    // Check if a user exists by email
    boolean existsByEmail(String email);

    Optional<User> findByVerificationToken(String token);
}

package com.example.admin.Service;

import com.example.admin.DTO.OrderDTO;
import com.example.admin.DTO.UserDTO;
import com.example.admin.Entity.User;
import com.example.admin.Repository.UserRepository;
import com.example.admin.exception.UserNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession session;

    @Autowired
    private ProfileImageService profileImageService;
    @Autowired
    private EmailService emailService;

    // Method to save a user (used during signup or updating user information)
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Method to retrieve a user by their email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Method to retrieve a user by their mobile number
    public Optional<User> getUserByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber);
    }

    // Method to handle login using either email or mobile number
    public Optional<User> login(String identifier, String password) {
        // Try to find the user by email first
        Optional<User> user = userRepository.findByEmail(identifier);

        // If not found by email, try to find by mobile number
        if (!user.isPresent()) {
            user = userRepository.findByMobileNumber(identifier);
        }

        // Check if password matches the found user's password
        return user.filter(u -> u.getPassword().equals(password));
    }

    // Method to retrieve a user by their ID (used for viewing profile)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAvatarUrl(user.getAvatarUrl());
        // Map orders to OrderDTOs
        dto.setOrders(user.getOrders().stream()
                .map(order -> {
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setOrderId(order.getOrderId());

                    // Map other fields
                    return orderDTO;
                })
                .collect(Collectors.toSet()));
        return dto;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void sendOfferEmail(List<String> emailAddresses, String subject, String message) {
        emailService.sendEmailToUsers(emailAddresses, subject, message);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    public void save(User user) {
    }


    public User getCurrentUser() {
        // Retrieve the user ID or email stored in the session
        Long userId = (Long) session.getAttribute("currentUserId");  // Assuming you store the userId

        // If the session contains the user ID, fetch the user from the database
        if (userId != null) {
            return userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userId));
        }

        // Return null or throw an exception if no user is in the session
        throw new UserNotFoundException("No user logged in");
    }

    // Method to set the current user in the session (called when logging in)
    public void getUserByUsername(User user) {
        session.setAttribute("currentUserId", user.getId());  // Store user ID in the session
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    /**
     * Compares two passwords for equality.
     */
    public boolean checkPassword(String password, String password1) {
        return password != null && password.equals(password1);}


}

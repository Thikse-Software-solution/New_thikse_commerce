package com.example.admin.Controller;

import com.example.admin.Entity.User;
import com.example.admin.Repository.UserRepository;
import com.example.admin.Service.EmailService;
import com.example.admin.Service.ProfileImageService;
import com.example.admin.Service.UserService;
import com.example.admin.request.LoginRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/users")
// Cross-origin for Angular app
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileImageService profileImageService;

    // ------------------ Registration Endpoints ------------------ //
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        // Check if the user already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            response.put("message", "Email already exists");
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response); // Return JSON response
        }

        // Generate profile image as a byte array (based on name and email)
        byte[] avatarImage = profileImageService.generateProfileImage(user.getName(), user.getEmail());
        user.setAvatarUrl(avatarImage);  // Store image as byte array

        // Generate a verification token (instead of OTP)
        String verificationToken = UUID.randomUUID().toString();
        user.setVerificationToken(verificationToken);
        user.setTokenGeneratedTime(LocalDateTime.now()); // Set token generation time
        user.setIsVerified(false); // Set verified to false by default

        // Save the user with the verification token
        userRepository.save(user);

        // Create verification link
        String verificationLink = "http://localhost:4200/E-coomerce/verify?token=" + user.getVerificationToken();

        // Send the verification email with the link
        String subject = "Account Verification";
        String message = "Please verify your account by clicking the following link: " + verificationLink;
        emailService.sendEmail(user.getEmail(), subject, message);

        // Prepare success response
        response.put("message", "Verification email sent to your email address.");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);  // Return JSON response
    }
    // ------------------ verification Endpoints ------------------ //
    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyUser(@RequestParam("token") String token) {
        // Find the user by the verification token
        Optional<User> userOptional = userRepository.findByVerificationToken(token);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Check if the token is expired (optional)
            if (user.getTokenGeneratedTime().isBefore(LocalDateTime.now().minusHours(24))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Verification link expired."));
            }

            // Mark user as verified
            user.setIsVerified(true);
            user.setVerificationToken(null); // Remove the token after verification
            userRepository.save(user);

            return ResponseEntity.ok(Map.of("message", "Account successfully verified."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid verification token."));
        }
    }

    // ------------------  Login Endpoints ------------------ //
//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
//        Optional<User> userOptional = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//
//            if (Boolean.TRUE.equals(user.getIsVerified())) {
//                // If verified, return login successful with 200 status
//                return ResponseEntity.ok("Login successful.");
//            } else {
//                // If not verified, send verification email and return a forbidden response
//                String verificationLink = "http://192.168.1.6:4200/E-coomerce/verify?token=" + user.getVerificationToken();
//                String subject = "Account Verification Reminder";
//                String message = "You need to verify your account before logging in. Please verify your account by clicking the following link: " + verificationLink;
//                emailService.sendEmail(user.getEmail(), subject, message);
//
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account is not verified. A verification email has been resent.");
//            }
//        } else {
//            // If the user is not found, return an unauthorized response
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials.");
//        }
//    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        // Find the user by email and password
        Optional<User> userOptional = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        // Check if the user exists (email and password combination is correct)
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Check if the user is verified
            if (Boolean.TRUE.equals(user.getIsVerified())) {
                // If verified, allow login
                return ResponseEntity.ok(user);
            } else {
                // If not verified, send verification email and return a forbidden response
                String verificationLink = "http://localhost:4200/E-coomerce/verify?token=" + user.getVerificationToken();
                String subject = "Account Verification Reminder";
                String message = "You need to verify your account before logging in. Please verify your account by clicking the following link: " + verificationLink;
                emailService.sendEmail(user.getEmail(), subject, message);

                Map<String, String> response = new HashMap<>();
                response.put("message", "Account is not verified. A verification email has been resent.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response);  // Return JSON response
            }
        } else {
            // Return a JSON error response when login fails
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid email or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);  // Return JSON response
        }
    }



    // ------------------ Profile Endpoints ------------------ //

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getUserProfileByEmail(@RequestParam String email) {
        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("mobileNumber", user.getMobileNumber());
            response.put("dob", user.getDob());
            response.put("gender", user.getGender());
            response.put("avatarUrl", Base64.getEncoder().encodeToString(user.getAvatarUrl()));  // Convert byte[] to base64 string
            response.put("address", user.getAddresses());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateProfileById(
            @PathVariable Long id,
            @RequestPart("user") String userJson,  // User data as JSON
            @RequestPart(value = "avatar", required = false) MultipartFile avatarFile) {

        // Convert the JSON string to a User object using ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Handle LocalDate if required

        User updatedUser;
        try {
            updatedUser = objectMapper.readValue(userJson, User.class); // Deserialize JSON to User
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid user data format: " + e.getMessage()));
        }

        // Fetch the existing user by ID
        Optional<User> existingUserOpt = userService.getUserById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // Update fields
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setMobileNumber(updatedUser.getMobileNumber());
            existingUser.setDob(updatedUser.getDob());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setAddresses(updatedUser.getAddresses());

            // Handle avatar file
            if (avatarFile != null && !avatarFile.isEmpty()) {
                try {
                    byte[] avatar = avatarFile.getBytes();
                    existingUser.setAvatarUrl(avatar); // Update avatar
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Map.of("error", "Error uploading avatar"));
                }
            }

            userService.saveUser(existingUser); // Save the updated user
            return ResponseEntity.ok(Map.of("message", "User profile updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User with id " + id + " not found"));
        }
    }


    // ------------------ Password Management Endpoints ------------------ //

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found."));
        }

        emailService.sendPasswordResetInstructions(email);
        return ResponseEntity.ok(Map.of("message", "Password reset instructions sent."));
    }
// ------------------ Reset Password Management Endpoints ------------------ //


    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        User user = userOpt.get();
        user.setPassword(newPassword);  // Password should be hashed in production
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Password reset successfully."));
    }
}

//
//@PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
//        // Find the user by email (or other identifier)
//        Optional<User> userOptional = userService.login(loginRequest.getIdentifier(), loginRequest.getPassword());
//
//        // Check if user exists
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//
//            // Check if the user is verified (OTP verified)
//            if (Boolean.TRUE.equals(user.getIsVerified())) {
//                // If verified, allow login
//                return ResponseEntity.ok("Login successful.");
//            } else {
//                // If not verified, send verification email and return a forbidden response
//                String subject = "Please Verify Your Email id ";
//                String message = "You attempted to log in without verifying your Emailid. Please verify your Email Id  using this link: <OTP_VERIFICATION_LINK>";
//                emailService.sendEmail(user.getEmail(), subject, message);
//
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account is not verified. Please verify your Email Id . A verification email has been sent.");
//            }
//        } else {
//            // If the user is not found, return an unauthorized response
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials.");
//        }
//    }

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody User user) {
//        // Check if the user already exists
//        if (userRepository.existsByEmail(user.getEmail())) {
//            return ResponseEntity.badRequest().body("Email already exists");
//        }
//
//        // Generate OTP and send it to the user's email
//        String otp = emailService.generateOtp();
//        user.setOtp(otp);
//        user.setIsVerified(false); // Set verified to false by default
//
//        // Save the user with OTP
//        userRepository.save(user);
//
//        // Send the OTP to the user's email
//        emailService.sendOtp(user.getEmail(), otp);
//
//        return ResponseEntity.ok("OTP sent to your email.");
//    }
    // ------------------ OTP Regenerate  Management Endpoints ------------------ //
//    @PostMapping("/regenerate-otp")
//    public ResponseEntity<String> regenerateOtp(@RequestBody OtpRequest otpRequest) {
//        // Validate the request
//        if (otpRequest.getEmail() == null || otpRequest.getEmail().isEmpty()) {
//            return ResponseEntity.badRequest().body("Email is required.");
//        }
//        Optional<User> userOptional = Optional.ofNullable(userService.findByEmail(otpRequest.getEmail()));
//        System.out.println("User found: " + userOptional.isPresent()); // This will help you debug
//        User user = userOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found."));
//
//        // Check if the user is already verified
//        if (Boolean.TRUE.equals(user.getIsVerified())) {
//            return ResponseEntity.badRequest().body("User is already verified.");
//        }
//
//        // Generate a new OTP
//        String newOtp = generateOtp(); // OTP generation logic
//        user.setOtp(newOtp);
//        user.setOtpGeneratedTime(LocalDateTime.now()); // Track OTP generation time
//        userRepository.save(user); // Save user with new OTP
//
//        // Send the new OTP via email/SMS
//        sendOtpToUser(user.getEmail(), newOtp); // Send OTP (method to be implemented)
//
//        return ResponseEntity.ok("A new OTP has been sent to your email.");
//    }
//
//// Helper methods
//
//    // OTP generation method
//    private String generateOtp() {
//        // Example: Generating a 6-digit random number as an OTP
//        return String.valueOf((int) ((Math.random() * 900000) + 100000));
//    }
//
//    // Send OTP method (implementation required based on your email/SMS service)
//    private void sendOtpToUser(String email, String otp) {
//        // Use an email service (e.g., JavaMailSender) to send the OTP
//        // Example: You can inject JavaMailSender and use it to send an email
//    }
//
//    @PostMapping("/regenerate-otp")
//    public ResponseEntity<String> regenerateOtp(@RequestBody OtpRequest otpRequest) {
//        // Validate the request
//        if (otpRequest.getEmail() == null || otpRequest.getEmail().isEmpty()) {
//            return ResponseEntity.badRequest().body("Email is required.");
//        }

        // Find the user by email
//        Optional<User> userOptional = Optional.ofNullable(userService.findByEmail(otpRequest.getEmail()));
//        if (!userOptional.isPresent()) {
//            return ResponseEntity.badRequest().body("User not found.");
//        }
//
//        User user = userOptional.get();
//
//        // Check if the user is already verified
//        if (Boolean.TRUE.equals(user.getIsVerified())) {
//            return ResponseEntity.badRequest().body("User is already verified.");
//        }
//
//        // Generate a new OTP
//        String newOtp = emailService.generateOtp();
//        user.setOtp(newOtp);
//        user.setOtpGeneratedTime(LocalDateTime.now()); // Track OTP generation time
//        userRepository.save(user); // Save user with new OTP
//
//        // Generate OTP link using the EmailService
//        String otpLink = emailService.generateOtpLink(user.getEmail(), newOtp);
//
//        // Send the new OTP link via email
//        try {
//            emailService.sendOtpLinkToUser(user.getEmail(), otpLink);
//            return ResponseEntity.ok("A new OTP has been sent to your email.");
//        } catch (MessagingException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to send OTP. Please try again later.");
//        }
//    }
//
//    // Helper methods
//    private String generateOtp() {
//        // Example: Generating a 6-digit random number as an OTP
//        return String.valueOf((int) ((Math.random() * 900000) + 100000));
//    }
//        // Verify the OTP submitted by the user
//        @PostMapping("/verify-otp")
//        public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest otpRequest) {
//            // Validate the request
//            if (otpRequest.getEmail() == null || otpRequest.getOtp() == null) {
//                return ResponseEntity.badRequest().body("Email and OTP are required.");
//            }
//
//            // Find the user by email
//            Optional<User> optionalUser = userRepository.findByEmail(otpRequest.getEmail());
//
//            // Check if the user is found
//            if (!optionalUser.isPresent()) {
//                return ResponseEntity.badRequest().body("User not found.");
//            }
//
//            // Extract the user from the Optional
//            User user = optionalUser.get();
//
//            // Check if the OTP matches
//            if (user.getOtp().equals(otpRequest.getOtp())) {
//                // If OTP matches, mark the user as verified
//                user.setIsVerified(true);  // Set the isVerified field to true
//                user.setOtp(null);         // Clear the OTP after verification
//
//                log.info("User verification status before save: " + user.getIsVerified());
//
//                // Save the updated user
//                userRepository.save(user);
//
//                log.info("User verification status after save: " + user.getIsVerified());
//
//                return ResponseEntity.ok("OTP verified successfully. User registered!");
//            } else {
//                return ResponseEntity.badRequest().body("Invalid OTP.");
//            }
//        }
//
//
//}
package com.example.admin.Service;

import com.example.admin.Entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, User> userStorage = new HashMap<>();

    // Method to generate a 6-digit OTP
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    // Store OTP associated with the email
    public void storeOtp(String email, String otp) {
        otpStorage.put(email, otp);
    }

    // Store the user temporarily until OTP is verified
    public void storeUser(String email, User user) {
        userStorage.put(email, user);
    }

    // Verify if the OTP is correct
    public boolean verifyOtp(String email, String otp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(otp);
    }

    // Get user data after OTP verification
    public User getUserByEmail(String email) {
        return userStorage.get(email);
    }

    // Clear OTP and temporary user data after successful registration
    public void clearOtp(String email) {
        otpStorage.remove(email);
        userStorage.remove(email);
    }
    private String generateNewOtp() {
        // Generate a random 6-digit OTP
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

}

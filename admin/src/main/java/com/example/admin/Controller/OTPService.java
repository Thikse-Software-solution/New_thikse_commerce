package com.example.admin.Controller;

import com.example.admin.Repository.OTPRepository;
import com.example.admin.Service.EmailService;


public class OTPService {
    private final EmailService emailService = new EmailService();
    private final OTPGenerator otpGenerator = new OTPGenerator();
    private final OTPRepository otpRepository = new OTPRepository(); // Simulated storage

    public void sendOTP(String recipientEmail) {
        String otp = otpGenerator.generateOTP();
        String subject = "Your OTP Code";
        String body = "Your OTP code is: " + otp;

        try {
            emailService.sendEmail(recipientEmail, subject, body);
            otpRepository.storeOTP(recipientEmail, otp); // Store OTP for validation
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception or notify the user
        }
    }

    public boolean verifyOTP(String email, String otp) {
        String storedOTP = otpRepository.getOTP(email);
        return storedOTP != null && storedOTP.equals(otp);
    }
}

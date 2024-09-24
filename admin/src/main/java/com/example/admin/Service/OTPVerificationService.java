package com.example.admin.Service;

import com.example.admin.Repository.OTPRepository;

public class OTPVerificationService {
    private final OTPRepository otpRepository = new OTPRepository();

    public boolean verifyOTP(String email, String otp) {
        String storedOTP = otpRepository.getOTP(email);
        return storedOTP != null && storedOTP.equals(otp);
    }
}

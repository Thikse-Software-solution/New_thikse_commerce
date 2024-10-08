package com.example.admin.Repository;

import java.util.HashMap;
import java.util.Map;

public class OTPRepository {
    private final Map<String, String> otpStore = new HashMap<>();

    public void storeOTP(String email, String otp) {
        otpStore.put(email, otp);
    }

    public String getOTP(String email) {
        return otpStore.get(email);
    }

    public void removeOTP(String email) {
        otpStore.remove(email);
    }
}

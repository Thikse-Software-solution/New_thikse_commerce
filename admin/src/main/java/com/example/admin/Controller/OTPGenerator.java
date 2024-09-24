package com.example.admin.Controller;

import java.security.SecureRandom;

public class OTPGenerator {
    private static final String CHARACTERS = "0123456789";
    private static final int LENGTH = 6;
    private static SecureRandom random = new SecureRandom();

    public static String generateOTP() {
        StringBuilder otp = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }
}


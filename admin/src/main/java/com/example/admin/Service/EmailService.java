package com.example.admin.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;



    private static final long EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes in milliseconds

    // Single email with HTML content
    public void sendEmail(String to, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // 'true' enables HTML support
            mailSender.send(mimeMessage);
            logger.info("Email sent to: {}", to);
        } catch (MessagingException e) {
            logger.error("Failed to send email", e);
            throw new RuntimeException("Email sending failed: " + e.getMessage(), e);
        }
    }

    // Password reset email with link expiration logic
    public void sendPasswordResetInstructions(String to) {
        long timestamp = new Date().getTime();
        String encodedTimestamp = URLEncoder.encode(String.valueOf(timestamp), StandardCharsets.UTF_8);

        String resetLink = "http://192.168.1.6:4200/E-coomerce/resetpassword?email=" +
                URLEncoder.encode(to, StandardCharsets.UTF_8) +
                "&timestamp=" + encodedTimestamp;

        String message = "Please click the following link to reset your password:\n" + resetLink;

        sendSimpleEmail(to, "Password Reset Instructions", message);
    }

    // Helper method for plain text emails (used for password reset)
    private void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        try {
            mailSender.send(message);
            logger.info("Simple email sent to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send simple email", e);
            throw new RuntimeException("Failed to send simple email: " + e.getMessage(), e);
        }
    }

    // Check if the password reset link has expired
    public boolean isLinkExpired(String timestamp) {
        try {
            long linkTimestamp = Long.parseLong(timestamp);
            long currentTime = new Date().getTime();
            return (currentTime - linkTimestamp) > EXPIRATION_TIME;
        } catch (NumberFormatException e) {
            logger.error("Invalid timestamp format", e);
            return true; // If parsing fails, treat the link as expired
        }
    }

    // Bulk email sending
    public void sendBulkEmail(List<String> recipients, String subject, String message) {
        for (String recipient : recipients) {
            sendEmail(recipient, subject, message);
        }
        logger.info("Bulk email sent to recipients: {}", recipients);
    }

    // Method to send the same email to a list of users
    public void sendEmailToUsers(List<String> emailAddresses, String subject, String message) {
        sendBulkEmail(emailAddresses, subject, message);
    }
    public void sendOfferEmail(String to, String subject, String text) {
        String shopName = "KPM Supershopee"; // Default shop name

        // Modify the subject to include the shop name
        String finalSubject = subject + " - " + shopName;

        // Modify the text to include the shop name at the end or beginning
        String finalText = text + "\n\nBest regards,\n" + shopName;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(finalSubject);
        message.setText(finalText);
        mailSender.send(message);
    }
    public void sendReminderEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);  // Set 'true' for HTML content
        mailSender.send(mimeMessage);
    }   public void sendOtp(String recipientEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }

    public String generateOtp() {
        return String.valueOf((int) (Math.random() * 900000) + 100000); // Generate 6-digit OTP
    }




    // Send OTP link method
    // Generate OTP link method
    public String generateOtpLink(String email, String otp) {
        String baseUrl = "http://192.168.1.6:4200/verify-otp"; // Replace with your frontend URL or API endpoint
        return baseUrl + "?email=" + email + "&otp=" + otp;
    }

    // Send OTP link method
    public void sendOtpLinkToUser(String email, String otpLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("Your OTP Verification Link");

        // HTML content with a clickable OTP link
        String htmlContent = "<p>Dear user,</p>"
                + "<p>Click the following link to verify your OTP:</p>"
                + "<a href=\"" + otpLink + "\">Verify OTP</a>"
                + "<p>This OTP will expire in 5 minutes.</p>"; // Customize as needed

        helper.setText(htmlContent, true); // Set true for HTML content

        // Send the email
        mailSender.send(message);
    }
}
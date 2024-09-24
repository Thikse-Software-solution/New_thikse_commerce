package com.example.admin.Service;

import com.example.admin.Entity.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final JavaMailSender mailSender;

    @Autowired
    public ContactService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void processContactForm(ContactForm contactForm) {
        // Send email to admin
        SimpleMailMessage adminMessage = new SimpleMailMessage();
        adminMessage.setTo("shineorganicskpm@gmail.com"); // Admin's email address
        adminMessage.setSubject("New Contact Form Submission");
        adminMessage.setText(buildAdminEmailBody(contactForm));
        mailSender.send(adminMessage);

        // Send confirmation email to the sender
        SimpleMailMessage senderMessage = new SimpleMailMessage();
        senderMessage.setTo(contactForm.getEmail()); // Sender's email address
        senderMessage.setSubject("Contact Form Submission Received");
        senderMessage.setText(buildSenderEmailBody(contactForm));
        mailSender.send(senderMessage);
    }

    private String buildAdminEmailBody(ContactForm contactForm) {
        return String.format("Name: %s\nEmail: %s\nPhone: %s\nMessage: %s",
                contactForm.getName(),
                contactForm.getEmail(),
                contactForm.getPhone(),
                contactForm.getMessage());
    }

    private String buildSenderEmailBody(ContactForm contactForm) {
        return String.format("Dear %s,\n\nThank you for reaching out to us. We have received your message:\n\n%s\n\nWe will get back to you soon.\n\nBest regards,\nKPM Supershopee",
                contactForm.getName(), contactForm.getMessage());
    }
}

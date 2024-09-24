package com.example.admin.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class ContactForm {
    @NotEmpty(message = "Name is required.")
    private String name;

    @NotEmpty(message = "Email is required.")
    @Email(message = "Please enter a valid email address.")
    private String email;

    @NotEmpty(message = "Phone number is required.")
    @Pattern(regexp = "^[0-9]{10}$", message = "Please enter a valid 10-digit phone number.")
    private String phone;

    @NotEmpty(message = "Message is required.")
    private String message;
    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

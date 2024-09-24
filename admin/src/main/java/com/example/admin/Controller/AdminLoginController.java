package com.example.admin.Controller;

import com.example.admin.Entity.Admin;
import com.example.admin.Entity.User;
import com.example.admin.Service.AdminService;
import com.example.admin.Service.EmailService;
import com.example.admin.Service.UserService;
import com.example.admin.request.OfferEmailRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminLoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @PostMapping("/adminlogin")
    public ResponseEntity<?> login(@RequestBody Admin request) {
        Admin admin = adminService.authenticate(request.getEmail(), request.getPassword());
        if (admin != null) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Invalid credentials"));
        }
    }

//    @PostMapping("/adminlogout")
//    public ResponseEntity<?> logout() {
//        // If using JWT, the logout endpoint typically does nothing on the server side.
//        // Notify client to remove JWT from local storage.
//        return ResponseEntity.ok(Collections.singletonMap("message", "Logout successful"));
//    }
    @GetMapping("/adminlogout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalidate the current session
        }
        return "redirect:/adminlogin"; // Redirect to login page after logout
    }
    @PostMapping("/send-offer")
    public ResponseEntity<?> sendOffer(@RequestBody OfferEmailRequest offerRequest) {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            emailService.sendOfferEmail(user.getEmail(), offerRequest.getSubject(), offerRequest.getMessage());
        }
        return ResponseEntity.ok("Offer emails sent successfully.");
    }
}

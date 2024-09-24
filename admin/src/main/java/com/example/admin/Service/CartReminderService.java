//package com.example.admin.Service;
//
//
//
//import com.example.admin.Entity.Cart;
//
//import com.example.admin.Repository.CartRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class CartReminderService {
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
//    public void checkCartItems() {
//        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
//        List<Cart> overdueCarts = cartRepository.findAllByAddedDateBefore(oneDayAgo);
//
//        for (Cart cart : overdueCarts) {
//            String customerEmail = cart.getUser().getEmail();
//            String productName = cart.getProduct().getName();
//            String emailBody = "You have an item in your cart for over a day: " + productName + ". Don’t miss out!";
//            emailService.sendEmail(customerEmail, "Reminder: Item in Your Cart", emailBody);
//        }
//    }
//}

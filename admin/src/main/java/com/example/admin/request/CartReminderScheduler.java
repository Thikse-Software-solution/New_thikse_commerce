//package com.example.admin.request;
//
//import com.example.admin.Entity.Cart;
//import com.example.admin.Entity.User;
//import com.example.admin.Service.CartService;
//import com.example.admin.Service.EmailService;
//import com.example.admin.Service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Component
//public class CartReminderScheduler {
//
//    @Autowired
//    private CartService cartService;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private UserService userService;
//
//    @Scheduled(cron = "0 0 0 * * ?")  // Runs daily at midnight
//    public void checkCartItemsAndSendReminders() {
//        List<Cart> cartItems = cartService.findItemsInCartForMoreThanOneDay();
//        for (Cart cartItem : cartItems) {
//            User user = cartItem.getUser();  // Get user associated with the cart item
//            String userEmail = user.getEmail();  // Get user email
//            String productName = cartItem.getProduct().getName();  // Get product name
//            String subject = "Reminder: Item still in your cart";
//            String content = "Dear " + user.getName() + ",\n\nYou have the item '" + productName + "' in your cart for over a day. Please review it.";
//            try {
//                emailService.sendReminderEmail(userEmail, subject, content);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}

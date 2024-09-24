//package com.example.admin.Service;
//
//import com.example.admin.Entity.Customer;
//import com.example.admin.Repository.CustomerRepository;
//import com.example.admin.request.CartReminderRequest;
//import com.example.admin.request.OfferEmailRequest;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CustomerServiceImpl implements CustomerService {
//
//    private final JavaMailSender mailSender;
//    private final CustomerRepository customerRepository;
//
//    public CustomerServiceImpl(JavaMailSender mailSender, CustomerRepository customerRepository) {
//        this.mailSender = mailSender;
//        this.customerRepository = customerRepository;
//    }
//
//    @Override
//    public void sendOfferEmail(OfferEmailRequest offerEmailRequest) {
//        List<Customer> customers = customerRepository.findAll();
//        for (Customer customer : customers) {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(customer.getEmail());
//            message.setSubject(offerEmailRequest.getSubject());
//            message.setText(offerEmailRequest.getMessage());
//            mailSender.send(message);
//        }
//    }
//
//
//
//    @Override
//    public void sendCartReminder(CartReminderRequest cartReminderRequest) {
//        List<Customer> customers = customerRepository.findCustomersWithItemsInCart();
//        for (Customer customer : customers) {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(customer.getEmail());
//            message.setSubject("Reminder: Items in Your Cart");
//            message.setText("You have items in your cart that you haven't purchased. Don't miss out!");
//            mailSender.send(message);
//        }
//    }
//
//
//}

//package com.example.admin.Entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//
//// OrderItem class (as an example)
//@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Table(name = "order_items")
//public class OrderItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "order_id") // This should match the foreign key in your database
//    private Order order;
//    private String productName;
//    private String customerName;
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public String getCustomerName() {
//        return customerName;
//    }
//
//    public void setCustomerName(String customerName) {
//        this.customerName = customerName;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public LocalDate getOrderDate() {
//        return orderDate;
//    }
//
//    public void setOrderDate(LocalDate orderDate) {
//        this.orderDate = orderDate;
//    }
//
//    public LocalDate getDeliveryDate() {
//        return deliveryDate;
//    }
//
//    public void setDeliveryDate(LocalDate deliveryDate) {
//        this.deliveryDate = deliveryDate;
//    }
//
//    public String getOrderStatus() {
//        return orderStatus;
//    }
//
//    public void setOrderStatus(String orderStatus) {
//        this.orderStatus = orderStatus;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPaymentStatus() {
//        return paymentStatus;
//    }
//
//    public void setPaymentStatus(String paymentStatus) {
//        this.paymentStatus = paymentStatus;
//    }
//
//    public Double getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(Double totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//    public String getRazorpayOrderId() {
//        return razorpayOrderId;
//    }
//
//    public void setRazorpayOrderId(String razorpayOrderId) {
//        this.razorpayOrderId = razorpayOrderId;
//    }
//
//    public String getRazorpayPaymentId() {
//        return razorpayPaymentId;
//    }
//
//    public void setRazorpayPaymentId(String razorpayPaymentId) {
//        this.razorpayPaymentId = razorpayPaymentId;
//    }
//
//    public String getRazorpaySignature() {
//        return razorpaySignature;
//    }
//
//    public void setRazorpaySignature(String razorpaySignature) {
//        this.razorpaySignature = razorpaySignature;
//    }
//
//    private String phoneNumber;
//    private LocalDate orderDate;
//    private LocalDate deliveryDate;
//    private String orderStatus;
//    private String address;
//    private String email;
//    private String paymentStatus;
//    private Double totalAmount;
//
//    // Razorpay specific fields
//    private String razorpayOrderId;
//    private String razorpayPaymentId;
//    private String razorpaySignature;
//
//    // Getters and Setters
//}

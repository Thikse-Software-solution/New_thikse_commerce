package com.example.admin.DTO;


public class PaymentDetails {
    private double amount; // Amount to be charged
    private String paymentMethod; // e.g., "RAZORPAY"
    private String orderId; // Razorpay order ID
    private String paymentId; // Razorpay payment ID

    // Constructors, getters, and setters
    public PaymentDetails() {}

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}

package com.example.admin.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RazorpayService {

    private final RazorpayClient razorpayClient;

    // Constructor to initialize RazorpayClient
    public RazorpayService(@Value("${razorpay.keyId}") String keyId,
                           @Value("${razorpay.keySecret}") String keySecret) throws RazorpayException {
        this.razorpayClient = new RazorpayClient(keyId, keySecret);
    }

    public Order createOrder(double amount) throws RazorpayException {
        // Prepare options as a HashMap
        Map<String, Object> options = new HashMap<>();
        options.put("amount", (int) (amount * 100)); // Amount in paise
        options.put("currency", "INR");
        options.put("receipt", "receipt#1");

        // Convert HashMap to JSONObject
        JSONObject jsonOptions = new JSONObject(options);

        // Create an order using the JSONObject
        return razorpayClient.orders.create(jsonOptions);
    }

    public boolean verifyPayment(String paymentId, String orderId, String signature) {
        // Implement payment verification logic here
        // This is a placeholder; you need to implement actual verification
        return true;
    }
}

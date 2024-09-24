package com.example.admin.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {

    private RazorpayClient razorpayClient;

    public PaymentService() throws Exception {
        this.razorpayClient = new RazorpayClient("rzp_test_KicENYodOfmrEn", "KD2UPauN4f4ipm4tArbwDkRX");
    }

    // Create order with dynamic receipt
    public JSONObject createOrder(double amount) throws Exception {
        JSONObject options = new JSONObject();
        options.put("amount", (int) (amount * 100));  // Razorpay expects the amount in paise
        options.put("currency", "INR");

        // Generate a unique receipt ID (you can use UUID or any other method)
        String receiptId = "txn_" + UUID.randomUUID().toString();
        options.put("receipt", receiptId);

        // Create an order in Razorpay and return the order details as a JSONObject
        Order order = razorpayClient.orders.create(options);
        return order.toJson();
    }

    // Verify payment signature
    public boolean verifyPaymentSignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        try {
            // Prepare the data to verify the signature
            Map<String, String> data = new HashMap<>();
            data.put("razorpay_order_id", razorpayOrderId);
            data.put("razorpay_payment_id", razorpayPaymentId);
            data.put("razorpay_signature", razorpaySignature);

            // Verify the signature using Razorpay's utility method
            return Utils.verifyPaymentSignature(new JSONObject(data), "06eGM62qchUZXd5NYY6PnDY1");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

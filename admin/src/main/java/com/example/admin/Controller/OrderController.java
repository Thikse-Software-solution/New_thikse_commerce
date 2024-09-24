package com.example.admin.Controller;

import com.example.admin.DTO.OrderDTO;
import com.example.admin.Entity.Order;
import com.example.admin.Service.OrderService;
import com.example.admin.Service.PaymentService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO) {
        try {
            // Calculate order amount, for example, from the OrderDTO
            double amount = orderDTO.getAmount();

            // Call the PaymentService to create a Razorpay order
            JSONObject razorpayOrder = paymentService.createOrder(amount);

            // Return the Razorpay order details in the response
            return ResponseEntity.ok(razorpayOrder.toString());
        } catch (Exception e) {
            logger.error("Error while placing order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error placing order.");
        }
    }
    @PostMapping("/verifyPayment")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> paymentDetails) {
        try {
            String razorpayOrderId = paymentDetails.get("razorpay_order_id");
            String razorpayPaymentId = paymentDetails.get("razorpay_payment_id");
            String razorpaySignature = paymentDetails.get("razorpay_signature");

            // Verify the payment using Razorpay's SDK or using your logic
            boolean isValid = paymentService.verifyPaymentSignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);

            if (isValid) {
                // Update order status to "paid"
                return ResponseEntity.ok("Payment verified successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment signature.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment verification failed.");
        }
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUser(userId);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            logger.error("Bad request while fetching orders for user ID {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Internal server error while fetching orders for user ID {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching orders.");
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.findOrderById(orderId);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            logger.error("Order not found with ID {}: {}", orderId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Internal server error while fetching order ID {}: {}", orderId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the order.");
        }
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody String status, @RequestParam Long userId) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(orderId, status, userId);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            logger.error("Bad request while updating order ID {} to status {}: {}", orderId, status, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Internal server error while updating order ID {} to status {}: {}", orderId, status, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the order.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            logger.error("Internal server error while fetching all orders: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching orders.");
        }
    }
}

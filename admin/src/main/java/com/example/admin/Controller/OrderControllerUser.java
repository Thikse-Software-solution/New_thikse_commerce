//package com.example.admin.Controller;
//
//
//import com.example.admin.Entity.OrderItem;
//import com.example.admin.Service.OrderServiceUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/user/orders")
//public class OrderControllerUser {
//    @Autowired
//    private OrderServiceUser orderServiceUser;
//
//    @GetMapping
//    public List<OrderItem> getAllOrders() {
//        return orderServiceUser.getAllOrders();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<OrderItem> getOrderById(@PathVariable Long id) {
//        OrderItem orderItem = orderServiceUser.getOrderById(id);
//        if (orderItem != null) {
//            return ResponseEntity.ok(orderItem);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping("create/{id}")
//    public OrderItem createOrder(@RequestBody OrderItem orderItem) {
//        return orderServiceUser.createOrder(orderItem);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<OrderItem> updateOrder(@PathVariable Long id, @RequestBody OrderItem orderDetails) {
//        OrderItem updatedOrder = orderServiceUser.updateOrder(id, orderDetails);
//        if (updatedOrder != null) {
//            return ResponseEntity.ok(updatedOrder);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
//        orderServiceUser.deleteOrder(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PostMapping("/verify")
//    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, Object> data) {
//        // Extract payment details from the request body
//        String razorpayOrderId = (String) data.get("razorpay_order_id");
//        String razorpayPaymentId = (String) data.get("razorpay_payment_id");
//        String razorpaySignature = (String) data.get("razorpay_signature");
//
//        // Verify the signature (implement your verification logic)
//        boolean isPaymentValid = verifySignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);
//
//        if (isPaymentValid) {
//            // Find and update the order with payment details
//            OrderItem orderItem = orderServiceUser.getOrderById(Long.valueOf((String) data.get("orderId")));
//            if (orderItem != null) {
//                orderItem.setRazorpayOrderId(razorpayOrderId);
//                orderItem.setRazorpayPaymentId(razorpayPaymentId);
//                orderItem.setRazorpaySignature(razorpaySignature);
//                orderItem.setPaymentStatus("Paid");
//                orderServiceUser.updateOrder(orderItem.getId(), orderItem);
//                return ResponseEntity.ok("Payment verified and order updated successfully.");
//            }
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment verification.");
//    }
//
//    private boolean verifySignature(String orderId, String paymentId, String signature) {
//        // Use Razorpay's SDK or custom logic to verify the signature
//        // Replace with actual verification logic
//        return true;
//    }
//
//}

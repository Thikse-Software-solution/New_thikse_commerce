//package com.example.admin.Controller;
//
//
//import com.example.admin.Entity.Payment;
//import com.example.admin.Service.PaymentService;
//import com.example.admin.Service.RazorpayService;
//import com.razorpay.Order;
//import com.razorpay.RazorpayException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/payment")
//public class RazorpayController {
//
//    @Autowired
//    private RazorpayService razorpayService;
//
//    @PostMapping(value = "/createOrder", produces = "application/json")
//    public ResponseEntity<Map<String, Object>> createOrder(@RequestParam double amount) throws RazorpayException {
//        Order order = razorpayService.createOrder(amount);
//
//        // Convert Order object to a Map
//        Map<String, Object> response = new HashMap<>();
//        response.put("id", order.get("id"));
//        response.put("amount", order.get("amount"));
//        response.put("currency", order.get("currency"));
//        response.put("status", order.get("status"));
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping(value = "/verifyPayment", produces = "application/json")
//    public ResponseEntity<Boolean> verifyPayment(@RequestParam String paymentId,
//                                                 @RequestParam String orderId,
//                                                 @RequestParam String signature) {
//        boolean isValid = razorpayService.verifyPayment(paymentId, orderId, signature);
//        return ResponseEntity.ok(isValid);
//    }
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @PostMapping(value = "/store", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<Payment> storePayment(@RequestBody Payment payment) {
//        Payment savedPayment = paymentService.savePayment(payment);
//        return ResponseEntity.ok(savedPayment);
//    }
//}

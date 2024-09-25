package com.example.admin.Controller;

import com.example.admin.DTO.OrderDTO;
import com.example.admin.Entity.Order;
import com.example.admin.Entity.Product;
import com.example.admin.Repository.OrderRepository;
import com.example.admin.Service.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
     private AddressService addressService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentService paymentService;
//    @PostMapping("/place")
//    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO) {
//        try {
//            // Calculate the order amount from OrderDTO
//            double amount = orderDTO.getAmount();
//
//            // Call the PaymentService to create a Razorpay order
//            JSONObject razorpayOrder = paymentService.createOrder(amount);
//
//            // Create a new Order entity to save in the database
//            Order order = new Order();
//            order.setOrderStatus("PENDING");  // Set initial order status as 'PENDING'
//            order.setAmount(amount);
//            order.setQuantity(orderDTO.getQuantity());  // Assuming OrderDTO has a quantity field
//            order.setOrderDate(LocalDate.now());
//            order.setDeliveryDate(orderDTO.getDeliveryDate());  // Assuming OrderDTO has a delivery date
//            order.setUser(userService.findUserById(orderDTO.getUserId()));  // Get user based on OrderDTO's userId
//            order.setOrderAddress(addressService.findById(orderDTO.getAddressId()));  // Get address from OrderDTO
//
//            // Find the product by ID and handle Optional
//            Product product = productService.findById(orderDTO.getProductId())
//                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + orderDTO.getProductId()));
//            order.setProduct(product);  // Set product details
//            order.setTotalPrice(amount * orderDTO.getQuantity());
//
//            // Save the order to the database
//            orderRepository.save(order);
//
//            // Return the Razorpay order details as response
//            return ResponseEntity.ok(razorpayOrder.toString());
//        } catch (IllegalArgumentException e) {
//            logger.error("Error while placing order: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (Exception e) {
//            logger.error("Error while placing order: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error placing order.");
//        }
//    }

//    @PostMapping("/place")
//    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO) {
//        try {
//            // Calculate the order amount from OrderDTO
//            double amount = orderDTO.getAmount();
//
//            // Call the PaymentService to create a Razorpay order
//            JSONObject razorpayOrder = paymentService.createOrder(amount);
//
//            // Create a new Order entity to save in the database
//            Order order = new Order();
//            order.setOrderStatus("PENDING");  // Set initial order status as 'PENDING'
//            order.setAmount(amount);
//            order.setQuantity(orderDTO.getQuantity());  // Assuming OrderDTO has a quantity field
//            order.setOrderDate(LocalDate.now());
//            order.setDeliveryDate(orderDTO.getDeliveryDate());  // Assuming OrderDTO has a delivery date
//            order.setUser(userService.findUserById(orderDTO.getUserId()));  // Get user based on OrderDTO's userId
//            order.setOrderAddress(addressService.findById(orderDTO.getAddressId()));  // Get address from OrderDTO
//
//            // Find the product by ID and handle Optional
//            Product product = productService.findById(orderDTO.getProductId())
//                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + orderDTO.getProductId()));
//            order.setProduct(product);  // Set product details
//            order.setTotalPrice(amount * orderDTO.getQuantity());
//
//            // Save the order to the database
//            orderRepository.save(order);
//
//            // Return the Razorpay order details as response
//            return ResponseEntity.ok(razorpayOrder.toString());
//        } catch (IllegalArgumentException e) {
//            logger.error("Error while placing order: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (Exception e) {
//            logger.error("Error while placing order: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error placing order.");
//        }
//    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO) {
        try {
            // Calculate the order amount from OrderDTO
            double amount = orderDTO.getAmount();

            // Calculate total price
            double totalPrice = amount * orderDTO.getQuantity();

            // Call the PaymentService to create a Razorpay order with total price
            JSONObject razorpayOrder = paymentService.createOrder(totalPrice);

            // Create a new Order entity to save in the database
            Order order = new Order();
            order.setOrderStatus("PENDING");  // Set initial order status as 'PENDING'
            order.setAmount(amount);
            order.setQuantity(orderDTO.getQuantity());  // Assuming OrderDTO has a quantity field
            order.setOrderDate(LocalDate.now());
            order.setDeliveryDate(orderDTO.getDeliveryDate());  // Assuming OrderDTO has a delivery date
            order.setUser(userService.findUserById(orderDTO.getUserId()));  // Get user based on OrderDTO's userId
            order.setOrderAddress(addressService.findById(orderDTO.getAddressId()));  // Get address from OrderDTO

            // Find the product by ID and handle Optional
            Product product = productService.findById(orderDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + orderDTO.getProductId()));
            order.setProduct(product);  // Set product details
            order.setTotalPrice(totalPrice);  // Set the total price for the order


            // Check if enough stock is available
            if (product.getStockQuantity() < orderDTO.getQuantity()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Not enough stock available for product: " + product.getName());
            }

            // Reduce the product stock
            product.setStockQuantity(product.getStockQuantity() - orderDTO.getQuantity());

            // Save the updated product stock in the database
            productService.saveProduct(product);  // Assuming the ProductService has a saveProduct method

            // Set product details in the order
            order.setProduct(product);
            order.setTotalPrice(totalPrice);  // Set the total price for the order

            // Save the order to the database
            orderRepository.save(order);

            // Return the Razorpay order details as response
            return ResponseEntity.ok(razorpayOrder.toString());
        } catch (IllegalArgumentException e) {
            logger.error("Error while placing order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error while placing order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error placing order.");
        }
    }

//
//    @PostMapping("/verifyPayment")
//    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> paymentDetails) {
//        try {
//            String razorpayOrderId = paymentDetails.get("razorpay_order_id");
//            String razorpayPaymentId = paymentDetails.get("razorpay_payment_id");
//            String razorpaySignature = paymentDetails.get("razorpay_signature");
//
//            // Verify the payment using Razorpay's SDK or using your logic
//            boolean isValid = paymentService.verifyPaymentSignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);
//
//            if (isValid) {
//                // Update order status to "paid"
//                return ResponseEntity.ok("Payment verified successfully.");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment signature.");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment verification failed.");
//        }
//    }
//
//
@PostMapping("/verifyPayment")
public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> paymentDetails) {
    try {
        String razorpayOrderId = paymentDetails.get("razorpay_order_id");
        String razorpayPaymentId = paymentDetails.get("razorpay_payment_id");
        String razorpaySignature = paymentDetails.get("razorpay_signature");

        // Verify the payment using Razorpay's SDK or your custom logic
        boolean isValid = paymentService.verifyPaymentSignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);

        if (isValid) {
            // Update order status to "Yet to Dispatch"
            Order order = orderService.findOrderByRazorpayOrderId(razorpayOrderId);
            if (order != null) {
                order.setOrderStatus("Yet to Dispatch");
                orderService.saveOrder(order);  // Save updated order status
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found with Razorpay Order ID: " + razorpayOrderId);
            }

            return ResponseEntity.ok("Payment verified successfully, order status updated to 'Yet to Dispatch'.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payment signature.");
        }
    } catch (Exception e) {
        logger.error("Error while verifying payment: {}", e.getMessage(), e);
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


//    @PutMapping("/{orderId}/status")
//    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody String status, @RequestParam Long userId) {
//        try {
//            // Trim the status to remove any surrounding whitespace and quotes
//            status = status.trim().replaceAll("^\"|\"$", "");
//
//            Order updatedOrder = orderService.updateOrderStatus(orderId, status, userId);
//            return ResponseEntity.ok(updatedOrder);
//        } catch (IllegalArgumentException e) {
//            logger.error("Bad request while updating order ID {} to status {}: {}", orderId, status, e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (Exception e) {
//            logger.error("Internal server error while updating order ID {} to status {}: {}", orderId, status, e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the order.");
//        }
//    }
@PutMapping("/{orderId}/status")
public ResponseEntity<?> updateOrderStatus(
        @PathVariable Long orderId,
        @RequestBody String status,
        @RequestParam Long userId) {
    try {
        // Trim the status to remove any surrounding whitespace and quotes
        status = status.trim().replaceAll("^\"|\"$", "");

        // Update order status using the provided userId
        Order updatedOrder = orderService.updateOrderStatus(orderId, status, userId);

        // Create a response object including both the updated order and the user ID
        Map<String, Object> response = new HashMap<>();
        response.put("order", updatedOrder);
        response.put("userId", userId);  // Return the user ID as part of the response

        return ResponseEntity.ok(response);

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

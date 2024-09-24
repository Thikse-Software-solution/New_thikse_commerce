package com.example.admin.Service;

import com.example.admin.DTO.OrderDTO;
import com.example.admin.Entity.Address;
import com.example.admin.Entity.Order;
import com.example.admin.Entity.Product;
import com.example.admin.Entity.User;
import com.example.admin.Repository.AddressRepository;
import com.example.admin.Repository.OrderRepository;
import com.example.admin.Repository.ProductRepository;
import com.example.admin.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public Order placeOrder(OrderDTO orderDTO) {
        logger.info("Placing order with details: UserId: {}, ProductId: {}, Quantity: {}, AddressId: {}",
                orderDTO.getUserId(), orderDTO.getProductId(), orderDTO.getQuantity(), orderDTO.getAddressId());

        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getStockQuantity() < orderDTO.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }

        Address address = addressRepository.findById(orderDTO.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setOrderAddress(address);
        order.setAmount(product.getPrice() * orderDTO.getQuantity());
        order.setQuantity(orderDTO.getQuantity());
        order.setOrderDate(Optional.ofNullable(orderDTO.getOrderDate()).orElse(LocalDate.now()));
        order.setDeliveryDate(Optional.ofNullable(orderDTO.getDeliveryDate()).orElse(order.getOrderDate().plusDays(3)));
        order.setOrderStatus(Optional.ofNullable(orderDTO.getOrderStatus()).orElse("PENDING"));

        product.setStockQuantity(product.getStockQuantity() - orderDTO.getQuantity());
        productRepository.save(product);

        Order savedOrder = orderRepository.save(order);
        logger.info("Order placed successfully with ID: {}", savedOrder.getOrderId());
        return savedOrder;
    }

    public List<Order> getOrdersByUser(Long userId) {
        logger.info("Fetching orders for user ID: {}", userId);
        if (userId == null) {
            throw new IllegalArgumentException("User ID must be provided");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return orderRepository.findByUser(user);
    }

    public Order findOrderById(Long orderId) {
        logger.info("Finding order by ID: {}", orderId);
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, String status, Long userId) {
        logger.info("Updating order ID: {} to status: {} for user ID: {}", orderId, status, userId);

        Order order = findOrderById(orderId);

        if (!order.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User does not have permission to update this order");
        }

        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid order status: " + status);
        }

        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    private boolean isValidStatus(String status) {
        return List.of("Processing", "Shipped", "Delivered", "Canceled").contains(status);
    }

    public List<Order> getAllOrders() {
        logger.info("Fetching all orders");
        return orderRepository.findAll();
    }
}

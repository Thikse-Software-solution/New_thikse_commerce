package com.example.admin.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pending_orders")
public class PendingOrder {
    @Id
    private Long orderId;
    private Long customerId;
    private String orderDate;
    private String orderStatus;
    private Double totalAmount;
    private String expectedShippingDate;
    private String items; // Consider using a more structured format or separate entities

    // Getters and Setters
}


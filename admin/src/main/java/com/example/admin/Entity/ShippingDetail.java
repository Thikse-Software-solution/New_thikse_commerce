package com.example.admin.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipping_details")
public class ShippingDetail {
    @Id
    private Long orderId;
    private Long customerId;
    private String shippingAddress;
    private String shippingMethod;
    private Double shippingCost;
    private String shippingStatus;
    private String estimatedDeliveryDate;
    private String actualDeliveryDate;
    private String carrier;

    // Getters and Setters
}

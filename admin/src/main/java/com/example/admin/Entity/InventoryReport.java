package com.example.admin.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class InventoryReport {

    @Id
    private String productId;
    private int stockQuantity;

    // Constructors
    public InventoryReport() {}

    public InventoryReport(String productId, int stockQuantity) {
        this.productId = productId;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}

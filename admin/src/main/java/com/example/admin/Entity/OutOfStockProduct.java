package com.example.admin.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "out_of_stock_products")
public class OutOfStockProduct {
    @Id
    private Long productId;
    private String productName;
    private String category;
    private Integer stockQuantity;
    private Integer thresholdLevel;
    private String lastRestockDate;
    private String expectedRestockDate;
    private String supplier;

}

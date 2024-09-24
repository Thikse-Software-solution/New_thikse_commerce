package com.example.admin.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sales_reports")
public class SalesReport {

    @Id
    private Long id; // Assuming you have an ID field
    private String date;
    private double totalSales;

    // Constructors
    public SalesReport() {}

    public SalesReport(Long id, String date, double totalSales) {
        this.id = id;
        this.date = date;
        this.totalSales = totalSales;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
}

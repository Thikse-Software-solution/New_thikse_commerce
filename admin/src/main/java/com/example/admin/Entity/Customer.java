package com.example.admin.Entity;


import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String text;

    @Lob
    @Column(length = 1000000)
    private byte[] customerImg; // Corrected naming convention

    // Constructors
    public Customer() {
    }

    public Customer(String name, byte[] customerImg, String text) { // Corrected parameter types
        this.name = name;
        this.customerImg = customerImg; // Fixed assignment
        this.text = text;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getCustomerImg() {
        return customerImg; // Added getter for customerImg
    }

    public void setCustomerImg(byte[] customerImg) {
        this.customerImg = customerImg; // Added setter for customerImg
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

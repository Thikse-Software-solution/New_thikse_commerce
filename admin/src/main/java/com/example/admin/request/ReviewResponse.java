package com.example.admin.request;


public class ReviewResponse {

    // A DTO for Review response with user details
    private Long id;
    private int rating;
    private String comment;
    private Long productId;
    private String userName;
    private byte[] userAvatar; // Avatar stored as byte array

    // Constructor
    public ReviewResponse(Long id, int rating, String comment, Long productId, String userName, byte[] userAvatar) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.productId = productId;
        this.userName = userName;
        this.userAvatar = userAvatar;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Long getProductId() {
        return productId;
    }

    public String getUserName() {
        return userName;
    }

    public byte[] getUserAvatar() {
        return userAvatar;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAvatar(byte[] userAvatar) {
        this.userAvatar = userAvatar;
    }
}
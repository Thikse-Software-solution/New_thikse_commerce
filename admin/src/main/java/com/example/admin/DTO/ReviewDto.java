package com.example.admin.DTO;

public class ReviewDto {
    private Long id;
    private Long productId;
    private UserDTO user;
    private String comment;
    private int rating;

    // Constructors
    public ReviewDto() {}

    public ReviewDto(Long id, Long productId, Long userId, String comment, int rating) {
        this.id = id;
        this.productId = productId;
        this.comment = comment;
        this.rating = rating;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

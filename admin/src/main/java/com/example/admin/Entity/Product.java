package com.example.admin.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean favorited;
    private Boolean shine;
    private Boolean sheShine;
    private String subcategory;
    private String category;

    @Lob
    @Column(length = 1000000)
    private byte[] mainimage;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private List<Card> cards = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Lob
    @Column(length = 1000000)
    private List<byte[]> images = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Lob
    @Column(length = 1000000)
    private List<byte[]> threeDImages = new ArrayList<>();

    @Lob
    @Column(length = 1000000)
    private byte[] thumbnail;

    private String title;
    private String name;
    private String benefit;
    private String suitable;
    private String description;
    private String keybenefit;
    private String howToUse;
    private String ingredients;
    private String size;
    private Double mrp;
    private Double price;
    private Integer stockQuantity;
    private Double discount;
    private Double averageRating ; // Default to 0
    private Boolean feature;
    private Boolean trend;
    private Boolean special;
    private String specialLine;
    private String color;
//
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
//    private List<Review> reviews = new ArrayList<>();

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    public Boolean getShine() {
        return shine;
    }

    public void setShine(Boolean shine) {
        this.shine = shine;
    }

    public Boolean getSheShine() {
        return sheShine;
    }

    public void setSheShine(Boolean sheShine) {
        this.sheShine = sheShine;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getMainimage() {
        return mainimage;
    }

    public void setMainimage(byte[] mainimage) {
        this.mainimage = mainimage;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }

    public List<byte[]> getThreeDImages() {
        return threeDImages;
    }

    public void setThreeDImages(List<byte[]> threeDImages) {
        this.threeDImages = threeDImages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public String getSuitable() {
        return suitable;
    }

    public void setSuitable(String suitable) {
        this.suitable = suitable;
    }

    public String getKeybenefit() {
        return keybenefit;
    }

    public void setKeybenefit(String keybenefit) {
        this.keybenefit = keybenefit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHowToUse() {
        return howToUse;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Boolean getFeature() {
        return feature;
    }

    public void setFeature(Boolean feature) {
        this.feature = feature;
    }

    public Boolean getTrend() {
        return trend;
    }

    public void setTrend(Boolean trend) {
        this.trend = trend;
    }

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    public String getSpecialLine() {
        return specialLine;
    }

    public void setSpecialLine(String specialLine) {
        this.specialLine = specialLine;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
//
//    public List<Review> getReviews() {
//        return reviews;
//    }

//    public void setReviews(List<Review> reviews) {
//        this.reviews = reviews != null ? reviews : new ArrayList<>();
//        updateAverageRating();
//    }
//
//    public void addReview(Review review) {
//        if (this.reviews == null) {
//            this.reviews = new ArrayList<>();
//        }
//        this.reviews.add(review);
//        updateAverageRating();
//    }
//
//    private void updateAverageRating() {
//        if (reviews == null || reviews.isEmpty()) {
//            averageRating = 0.0;
//            return;
//        }
//
//        double totalRating = 0;
//        for (Review review : reviews) {
//            totalRating += review.getRating(); // Ensure this method returns a numeric value
//        }
//        averageRating = totalRating / reviews.size();
//    }
}

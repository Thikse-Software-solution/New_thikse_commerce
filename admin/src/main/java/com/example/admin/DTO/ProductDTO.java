package com.example.admin.DTO;

import java.util.List;

public class ProductDTO {
    private Long id;
    private Boolean favorited;
    private Integer rating;
    private Boolean shine;
    private Boolean sheShine;
    private String subcategory;
    private String category;
    private byte[] mainimage;
    private List<CardDTO> cards;
    private List<byte[]> images;      // Corrected to byte[]
    private List<byte[]> threeDImages; // Corrected to byte[]
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
    private Double averageRating;
    private Boolean feature;
    private Boolean trend;
    private Boolean special;
    private String specialLine;
    private String color;
    private List<ReviewDto> reviews;

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }

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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
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

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
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

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeybenefit() {
        return keybenefit;
    }

    public void setKeybenefit(String keybenefit) {
        this.keybenefit = keybenefit;
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
}

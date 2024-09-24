package com.example.admin.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String mobileNumber;

    private String password;

    private String gender;

    private String dob;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Review> reviews;

    // Optionally re-enable favorites functionality
    // @ManyToMany
    // private Set<Product> favorites = new HashSet<>();

    @Lob
    @Column(length = 1000000)
    private byte[] avatarUrl; // Renamed to avatar for clarity since it's a byte array

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Address> addresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private Set<Cart> carts;

    @OneToOne
    @JoinColumn(name = "selected_address_id")
    private Address selectedAddress;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Order> orders;


    private String verificationToken; // Field for verification token

    @Column(nullable = false)
    private Boolean isVerified = false;



    private LocalDateTime tokenGeneratedTime; // Store token generation time

    // Constructors
    public User(Long userId) {
        this.id = userId;
    }

    public User() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public byte[] getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(byte[] avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    // Favorites functionality (if re-enabled)
    // public Set<Product> getFavorites() {
    //     return favorites;
    // }
    //
    // public void setFavorites(Set<Product> favorites) {
    //     this.favorites = favorites;
    // }

    // Verification token and OTP fields

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }


    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public LocalDateTime getTokenGeneratedTime() {
        return tokenGeneratedTime;
    }

    public void setTokenGeneratedTime(LocalDateTime tokenGeneratedTime) {
        this.tokenGeneratedTime = tokenGeneratedTime;
    }
}

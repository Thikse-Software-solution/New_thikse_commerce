package com.example.admin.DTO;

import java.util.Set;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private byte[] avatarUrl;
    private Set<OrderDTO> orders;

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

    public Set<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderDTO> orders) {
        this.orders = orders;
    }

    public byte[] getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(byte[] avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
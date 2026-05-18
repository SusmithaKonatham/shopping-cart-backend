package com.shopping.shoppingbackend.dto;

import java.math.BigDecimal;

public class ProductResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private boolean active;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, BigDecimal price, String imageUrl, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.active = active;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

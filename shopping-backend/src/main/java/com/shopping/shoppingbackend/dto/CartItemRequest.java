package com.shopping.shoppingbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemRequest {

    @JsonProperty("product_id")
    @Positive(message = "Product ID must be positive")
    private Long productId;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("quantity")
    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

    // Default constructor
    public CartItemRequest() {
    }

    // Constructor
    public CartItemRequest(Long productId, String productName, BigDecimal price, Integer quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
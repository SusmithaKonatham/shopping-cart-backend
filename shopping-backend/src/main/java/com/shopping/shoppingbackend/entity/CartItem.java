package com.shopping.shoppingbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime addedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CartItem() {
    }

    public CartItem(Long id, String productName, double price) {
        this.id = id;
    }

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BigDecimal getLineTotal() {
        if (product != null && product.getPrice() != null && quantity != null) {
            return product.getPrice().multiply(new BigDecimal(quantity));
        }
        return BigDecimal.ZERO;
    }
}
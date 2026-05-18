package com.shopping.shoppingbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopping.shoppingbackend.entity.CartItem;
import com.shopping.shoppingbackend.entity.Product;

@Repository
public interface CartRepository
        extends JpaRepository<CartItem, Long> {

    List<CartItem> findByProductId(Long productId);

    void deleteByProduct(Product product);

    @Query("SELECT DISTINCT c FROM CartItem c LEFT JOIN FETCH c.product")
    List<CartItem> findAllWithProduct();

    Optional<CartItem> findByIdAndProductId(Long id, Long productId);
}
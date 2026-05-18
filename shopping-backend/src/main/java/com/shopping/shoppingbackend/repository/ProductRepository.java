package com.shopping.shoppingbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopping.shoppingbackend.entity.Product;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

    Optional<Product> findByIdAndActiveTrue(Long id);

    Optional<Product> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE p.active = true")
    List<Product> findAllActiveProducts();

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.cartItems WHERE p.id = :id")
    Optional<Product> findByIdWithCartItems(@Param("id") Long id);
}
package com.shopping.shoppingbackend.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shopping.shoppingbackend.dto.ApiResponse;
import com.shopping.shoppingbackend.dto.CartItemRequest;
import com.shopping.shoppingbackend.dto.CartItemResponse;
import com.shopping.shoppingbackend.service.ICartService;

@RestController
@RequestMapping({"/api/v1/cart", "/api/cart"})
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartItemResponse>> addToCart(
            @Valid @RequestBody CartItemRequest request) {
        CartItemResponse item = cartService.addToCart(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Item added to cart successfully", item));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> getCartItems() {
        List<CartItemResponse> items = cartService.getCartItems();
        return ResponseEntity.ok(
            new ApiResponse<>("Cart items retrieved successfully", items)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CartItemResponse>> updateCartItem(
            @PathVariable Long id,
            @Valid @RequestBody CartItemRequest request) {
        CartItemResponse item = cartService.updateCartItem(id, request);
        return ResponseEntity.ok(
            new ApiResponse<>("Cart item updated successfully", item)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(@PathVariable Long id) {
        cartService.deleteCartItem(id);
        return ResponseEntity.ok(
            new ApiResponse<>("Item deleted from cart successfully", null)
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok(
            new ApiResponse<>("Cart cleared successfully", null)
        );
    }
}
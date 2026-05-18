package com.shopping.shoppingbackend.service;

import java.util.List;

import com.shopping.shoppingbackend.dto.CartItemRequest;
import com.shopping.shoppingbackend.dto.CartItemResponse;

public interface ICartService {

    CartItemResponse addToCart(CartItemRequest request);

    List<CartItemResponse> getCartItems();

    CartItemResponse updateCartItem(Long id, CartItemRequest request);

    void deleteCartItem(Long id);

    void clearCart();
}

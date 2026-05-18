package com.shopping.shoppingbackend.mapper;

import com.shopping.shoppingbackend.dto.CartItemRequest;
import com.shopping.shoppingbackend.dto.CartItemResponse;
import com.shopping.shoppingbackend.entity.CartItem;

public class CartItemMapper {

    public static CartItemResponse toResponse(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        return new CartItemResponse(
            cartItem.getId(),
            cartItem.getProduct().getId(),
            cartItem.getProduct().getName(),
            cartItem.getProduct().getPrice(),
            cartItem.getQuantity(),
            cartItem.getLineTotal()
        );
    }

    public static CartItem toEntity(CartItemRequest request) {
        if (request == null) {
            return null;
        }
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(request.getQuantity());
        return cartItem;
    }

    public static void updateEntityFromRequest(CartItemRequest request, CartItem cartItem) {
        if (request == null || cartItem == null) {
            return;
        }
        if (request.getQuantity() != null) {
            cartItem.setQuantity(request.getQuantity());
        }
    }
}

package com.shopping.shoppingbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.shoppingbackend.dto.CartItemRequest;
import com.shopping.shoppingbackend.dto.CartItemResponse;
import com.shopping.shoppingbackend.entity.CartItem;
import com.shopping.shoppingbackend.entity.Product;
import com.shopping.shoppingbackend.exception.BusinessException;
import com.shopping.shoppingbackend.exception.ResourceNotFoundException;
import com.shopping.shoppingbackend.mapper.CartItemMapper;
import com.shopping.shoppingbackend.repository.CartRepository;
import com.shopping.shoppingbackend.repository.ProductRepository;

@Service
@Transactional
public class CartService implements ICartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartItemResponse addToCart(CartItemRequest request) {
        logger.debug("Adding item to cart - request: {}", request);

        if (request == null) {
            throw new BusinessException("VALIDATION_ERROR", "Cart request body is required");
        }

        Long productId = request.getProductId();
        String productName = request.getProductName();
        java.math.BigDecimal requestPrice = request.getPrice();
        Integer quantity = request.getQuantity() != null ? request.getQuantity() : 1;

        if (productId == null && (productName == null || productName.trim().isEmpty())) {
            throw new BusinessException("VALIDATION_ERROR", "Either productId or productName must be provided");
        }

        Product product = null;
        if (productId != null) {
            try {
                product = productRepository.findById(productId)
                        .orElseThrow(() -> {
                            logger.warn("Product not found for cart - productId: {}", productId);
                            return new ResourceNotFoundException("Product", "id", productId);
                        });
            } catch (IllegalArgumentException ex) {
                logger.warn("Invalid product id value passed to cart: {}", productId, ex);
                throw new BusinessException("VALIDATION_ERROR", "Invalid product ID");
            }
        } else {
            product = productRepository.findByName(productName)
                    .orElseThrow(() -> {
                        logger.warn("Product not found for cart by name: {}", productName);
                        return new ResourceNotFoundException("Product", "name", productName);
                    });
        }

        if (!product.isActive()) {
            logger.warn("Attempt to add inactive product to cart - productId: {}", product.getId());
            throw new BusinessException("PRODUCT_INACTIVE", 
                "Product is no longer available");
        }

        if (requestPrice != null && product.getPrice() != null && product.getPrice().compareTo(requestPrice) != 0) {
            logger.warn("Price mismatch for cart item - productId: {}, requestPrice: {}, productPrice: {}",
                    product.getId(), requestPrice, product.getPrice());
            throw new BusinessException("PRODUCT_PRICE_MISMATCH", 
                    "Product price does not match expected price");
        }

        try {
            CartItem cartItem = CartItemMapper.toEntity(request);
            cartItem.setQuantity(quantity);
            cartItem.setProduct(product);
            CartItem saved = cartRepository.save(cartItem);
            logger.info("Item added to cart successfully - cartItemId: {}, productId: {}, quantity: {}", 
                saved.getId(), product.getId(), saved.getQuantity());
            return new CartItemResponse(
                    saved.getId(),
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    saved.getQuantity(),
                    product.getPrice().multiply(new java.math.BigDecimal(saved.getQuantity()))
            );
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            logger.error("Data integrity error adding item to cart", ex);
            throw new BusinessException("CART_ADD_ERROR", "Failed to add item to cart due to invalid data");
        } catch (Exception ex) {
            logger.error("Error adding item to cart", ex);
            throw new BusinessException("CART_ADD_ERROR", "Failed to add item to cart");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemResponse> getCartItems() {
        logger.debug("Fetching all cart items");
        try {
            List<CartItem> items = cartRepository.findAllWithProduct();
            logger.info("Retrieved {} cart items", items.size());
            return items.stream()
                    .map(CartItemMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Error fetching cart items", ex);
            throw ex;
        }
    }

    @Override
    public CartItemResponse updateCartItem(Long id, CartItemRequest request) {
        logger.debug("Updating cart item - id: {}, quantity: {}", id, request.getQuantity());

        if (request == null || request.getQuantity() == null) {
            throw new BusinessException("VALIDATION_ERROR", "Quantity is required for cart update");
        }

        CartItem cartItem = cartRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cart item not found for update - id: {}", id);
                    return new ResourceNotFoundException("CartItem", "id", id);
                });

        CartItemMapper.updateEntityFromRequest(request, cartItem);
        CartItem updated = cartRepository.save(cartItem);
        
        logger.info("Cart item updated successfully - id: {}, quantity: {}", id, updated.getQuantity());
        return new CartItemResponse(
                updated.getId(),
                updated.getProduct().getId(),
                updated.getProduct().getName(),
                updated.getProduct().getPrice(),
                updated.getQuantity(),
                updated.getProduct().getPrice().multiply(new java.math.BigDecimal(updated.getQuantity()))
        );
    }

    @Override
    public void deleteCartItem(Long id) {
        logger.debug("Deleting cart item - id: {}", id);

        CartItem cartItem = cartRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cart item not found for deletion - id: {}", id);
                    return new ResourceNotFoundException("CartItem", "id", id);
                });

        try {
            cartRepository.deleteById(id);
            logger.info("Cart item deleted successfully - id: {}", id);
        } catch (Exception ex) {
            logger.error("Error deleting cart item - id: {}", id, ex);
            throw new BusinessException("CART_DELETE_ERROR", "Failed to delete cart item");
        }
    }

    @Override
    public void clearCart() {
        logger.debug("Clearing all cart items");
        try {
            cartRepository.deleteAll();
            logger.info("Cart cleared successfully");
        } catch (Exception ex) {
            logger.error("Error clearing cart", ex);
            throw new BusinessException("CART_CLEAR_ERROR", "Failed to clear cart");
        }
    }
}
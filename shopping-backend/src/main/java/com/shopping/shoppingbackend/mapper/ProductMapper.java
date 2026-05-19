package com.shopping.shoppingbackend.mapper;

import com.shopping.shoppingbackend.dto.ProductRequest;
import com.shopping.shoppingbackend.dto.ProductResponse;
import com.shopping.shoppingbackend.entity.Product;

public class ProductMapper {

    public static ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.isActive()
        );
    }

    public static Product toEntity(ProductRequest request) {
        if (request == null) {
            return null;
        }
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setActive(true);
        return product;
    }

    public static Product toEntity(String name, java.math.BigDecimal price, byte[] imageData, String contentType) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
//        product.setImageData(imageData);
//        product.setImageContentType(contentType);
        product.setActive(true);
        return product;
    }

    public static void updateEntityFromRequest(ProductRequest request, Product product) {
        if (request == null || product == null) {
            return;
        }
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getImageUrl() != null) {
            product.setImageUrl(request.getImageUrl());
        }
    }
}

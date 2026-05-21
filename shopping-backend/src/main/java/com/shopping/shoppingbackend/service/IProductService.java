package com.shopping.shoppingbackend.service;

import java.util.List;

import com.shopping.shoppingbackend.dto.ProductRequest;
import com.shopping.shoppingbackend.dto.ProductResponse;

public interface IProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse addProduct(ProductRequest request);

    ProductResponse addProduct(String name, java.math.BigDecimal price, org.springframework.web.multipart.MultipartFile image);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    void deactivateProduct(Long id);
    
    void activateProduct(Long id);
}

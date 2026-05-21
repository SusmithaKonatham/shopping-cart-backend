package com.shopping.shoppingbackend.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shopping.shoppingbackend.dto.ApiResponse;
import com.shopping.shoppingbackend.dto.ProductRequest;
import com.shopping.shoppingbackend.dto.ProductResponse;
import com.shopping.shoppingbackend.service.IProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/v1/products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(
            new ApiResponse<>("Products retrieved successfully", products)
        );
    }

    @GetMapping("/api/products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsLegacy() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(
            new ApiResponse<>("Products retrieved successfully", products)
        );
    }

    @GetMapping("/api/v1/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(
            new ApiResponse<>("Product retrieved successfully", product)
        );
    }

    @PostMapping("/api/v1/products")
    public ResponseEntity<ApiResponse<ProductResponse>> addProduct(
            @Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.addProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Product created successfully", product));
    }

    @PutMapping("/api/v1/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.updateProduct(id, request);
        return ResponseEntity.ok(
            new ApiResponse<>("Product updated successfully", product)
        );
    }

    @DeleteMapping("/api/v1/products/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(
            new ApiResponse<>("Product deleted successfully", null)
        );
    }

    @PatchMapping("/api/v1/products/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateProduct(@PathVariable Long id) {
        productService.deactivateProduct(id);
        return ResponseEntity.ok(
            new ApiResponse<>("Product deactivated successfully", null)
        );
    }

    // Legacy endpoints for backward compatibility
    @PostMapping("/api/products")
    public ResponseEntity<ApiResponse<ProductResponse>> addProductLegacy(
            @Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.addProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Product created successfully", product));
    }

    @PutMapping("/api/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProductLegacy(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.updateProduct(id, request);
        return ResponseEntity.ok(
            new ApiResponse<>("Product updated successfully", product)
        );
    }

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductLegacy(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(
            new ApiResponse<>("Product deleted successfully", null)
        );
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductByIdLegacy(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(
            new ApiResponse<>("Product retrieved successfully", product)
        );
    }

//    @PatchMapping("/api/products/{id}/deactivate")
//    public ResponseEntity<ApiResponse<Void>> deactivateProductLegacy(@PathVariable Long id) {
//        productService.deactivateProduct(id);
//        return ResponseEntity.ok(
//            new ApiResponse<>("Product deactivated successfully", null)
//        );
//    }
    @PatchMapping("/api/v1/products/{id}/activate")
    public ResponseEntity<ApiResponse<Void>> activateProduct(
            @PathVariable Long id) {

       productService.activateProduct(id);

        return ResponseEntity.ok(
              new  ApiResponse<>(
                        "Product activated successfully",
                        null )
        );
    }
}
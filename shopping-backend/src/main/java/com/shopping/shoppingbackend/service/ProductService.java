package com.shopping.shoppingbackend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.shopping.shoppingbackend.dto.ProductRequest;
import com.shopping.shoppingbackend.dto.ProductResponse;
import com.shopping.shoppingbackend.entity.Product;
import com.shopping.shoppingbackend.exception.BusinessException;
import com.shopping.shoppingbackend.exception.ResourceNotFoundException;
import com.shopping.shoppingbackend.mapper.ProductMapper;
import com.shopping.shoppingbackend.repository.CartRepository;
import com.shopping.shoppingbackend.repository.ProductRepository;

@Service
@Transactional
public class ProductService implements IProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        logger.debug("Fetching all active products");
        try {
            List<Product> products = productRepository.findByActiveTrue();
            logger.info("Retrieved {} active products", products.size());
            return products.stream()
                    .map(ProductMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            logger.error("Error fetching products", ex);
            throw ex;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        logger.debug("Fetching product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product not found with id: {}", id);
                    return new ResourceNotFoundException("Product", "id", id);
                });
        
        if (!product.isActive()) {
            logger.warn("Attempt to access inactive product with id: {}", id);
            throw new ResourceNotFoundException("Product", "id", id);
        }
        
        logger.info("Product retrieved successfully - id: {}, name: {}", id, product.getName());
        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse addProduct(ProductRequest request) {
        logger.debug("Adding new product: name={}, price={}", request.getName(), request.getPrice());
        
        if (productRepository.existsByName(request.getName())) {
            logger.warn("Attempt to create duplicate product: {}", request.getName());
            throw new BusinessException("PRODUCT_DUPLICATE", 
                "Product with name '" + request.getName() + "' already exists");
        }

        try {
            Product product = ProductMapper.toEntity(request);
            Product saved = productRepository.save(product);
            logger.info("Product created successfully - id: {}, name: {}", saved.getId(), saved.getName());
            return ProductMapper.toResponse(saved);
        } catch (Exception ex) {
            logger.error("Error creating product", ex);
            throw new BusinessException("PRODUCT_CREATE_ERROR", "Failed to create product");
        }
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        logger.debug("Updating product with id: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product not found for update: id={}", id);
                    return new ResourceNotFoundException("Product", "id", id);
                });

        ProductMapper.updateEntityFromRequest(request, product);
        Product updated = productRepository.save(product);
        
        logger.info("Product updated successfully - id: {}, name: {}", id, updated.getName());
        return ProductMapper.toResponse(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        logger.debug("Deleting product with id: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product not found for deletion: id={}", id);
                    return new ResourceNotFoundException("Product", "id", id);
                });

        try {
            cartRepository.deleteByProduct(product);
            productRepository.deleteById(id);
            logger.info("Product deleted successfully - id: {}, name: {}", id, product.getName());
        } catch (Exception ex) {
            logger.error("Error deleting product with id: {}", id, ex);
            throw new BusinessException("PRODUCT_DELETE_ERROR", "Failed to delete product");
        }
    }

    @Override
    public void deactivateProduct(Long id) {
        logger.debug("Deactivating product with id: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product not found for deactivation: id={}", id);
                    return new ResourceNotFoundException("Product", "id", id);
                });

        try {
            cartRepository.deleteByProduct(product);
            product.setActive(false);
            productRepository.save(product);
            logger.info("Product deactivated successfully - id: {}, name: {}", id, product.getName());
        } catch (Exception ex) {
            logger.error("Error deactivating product with id: {}", id, ex);
            throw new BusinessException("PRODUCT_DEACTIVATE_ERROR", "Failed to deactivate product");
        }
    }

	@Override
	public ProductResponse addProduct(String name, BigDecimal price, MultipartFile image) {
		// TODO Auto-generated method stub
		return null;
	}
}
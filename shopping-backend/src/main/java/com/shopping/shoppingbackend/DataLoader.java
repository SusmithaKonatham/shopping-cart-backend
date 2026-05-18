package com.shopping.shoppingbackend;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shopping.shoppingbackend.entity.Product;
import com.shopping.shoppingbackend.repository.ProductRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(ProductRepository repository) {
        return args -> {

            Product product1 = new Product();
            product1.setName("iPhone 15");
            product1.setPrice(new BigDecimal("80000"));
            product1.setImageUrl("https://images.unsplash.com/photo-1511707171634-5f897ff02aa9");
            product1.setActive(true);
            repository.save(product1);

            Product product2 = new Product();
            product2.setName("Laptop");
            product2.setPrice(new BigDecimal("70000"));
            product2.setImageUrl("https://images.unsplash.com/photo-1496181133206-80ce9b88a853");
            product2.setActive(true);
            repository.save(product2);

            Product product3 = new Product();
            product3.setName("Samsung TV");
            product3.setPrice(new BigDecimal("55000"));
            product3.setImageUrl("https://images.unsplash.com/photo-1593784991095-a205069470b6");
            product3.setActive(true);
            repository.save(product3);
        };
    }
}	
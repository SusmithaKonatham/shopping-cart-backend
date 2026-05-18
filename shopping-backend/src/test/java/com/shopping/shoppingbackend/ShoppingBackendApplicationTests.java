package com.shopping.shoppingbackend;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.shoppingbackend.dto.ProductRequest;
import com.shopping.shoppingbackend.repository.ProductRepository;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
		assertNotNull(mockMvc);
	}

	@Test
	void testGetAllProducts() throws Exception {
		mockMvc.perform(get("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.message").exists());
	}

	@Test
	void testAddProduct() throws Exception {
		ProductRequest request = new ProductRequest();
		request.setName("Test Product");
		request.setPrice(new BigDecimal("99.99"));
		request.setImageUrl("https://example.com/image.jpg");

		mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.name").value("Test Product"));
	}

	@Test
	void testAddProductWithInvalidData() throws Exception {
		String invalidRequest = "{}";

		mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(invalidRequest))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
	}

	@Test
	void testGetProductNotFound() throws Exception {
		mockMvc.perform(get("/api/v1/products/999")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"));
	}
}

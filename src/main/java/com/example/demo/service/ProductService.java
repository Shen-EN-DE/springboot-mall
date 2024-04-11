package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.constent.ProductCategory;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;

@Component
public interface ProductService {
	
	Product getProductById(Integer productId);

	List<Product> getProducts(ProductCategory category, String search);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	
	void deleteProductById(Integer productId);

	
}

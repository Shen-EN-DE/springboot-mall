package com.example.demo.service;

import org.springframework.stereotype.Component;

import com.example.demo.model.Product;

@Component
public interface ProductService {
	
	Product getProductById(Integer productId);

}

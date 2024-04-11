package com.example.demo.dao;

import org.springframework.stereotype.Component;

import com.example.demo.model.Product;

@Component
public interface ProductDao {
	Product getProductById(Integer productId);
}

package com.example.demo.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.constent.ProductCategory;
import com.example.demo.dao.ProductDao;
import com.example.demo.dto.ProductQueryParams;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDao productDao;
	
	@Override
	public Product getProductById(Integer productId) {
		
		return productDao.getProductById(productId);
		
	}
	
	@Override
	public Integer countProduct(ProductQueryParams productQueryParams) {
		return productDao.countProduct(productQueryParams);
	}


	@Override
	public List<Product> getProducts(ProductQueryParams productQueryParams) {
		
		return productDao.getProducts(productQueryParams);
	}


	@Override
	public Integer createProduct(ProductRequest productRequest) {
		
		Integer productId =  productDao.createProduct(productRequest);
		
		return productId;
	}
	

	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		
		productDao.updateProduct(productId, productRequest);
	}

	@Override
	public void updateProductWithExistingImage(Integer productId, String productName, 
			ProductCategory category, String oldImageUrl, Integer price,
			Integer stock, String description) {
		
		Product product = productDao.getProductById(productId);
		
		product.setProductName(productName);
		product.setCategery(category);
		product.setImageUrl(oldImageUrl);
		product.setPrice(price);
		product.setstock(stock);
		product.setDescription(description);
		
		productDao.updateProductWithExistingImage(productId, product);
		
		
		
	}
	
	

	@Override
	public void deleteProductById(Integer productId) {
		
		productDao.deleteProductById(productId);
		
	}
	
}

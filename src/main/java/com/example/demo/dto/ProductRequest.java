package com.example.demo.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.constent.ProductCategory;

import jakarta.validation.constraints.NotNull;

public class ProductRequest {

	@NotNull
	private String productName;
	
	@NotNull
	private ProductCategory category;
	
//	@NotNull
	private MultipartFile imageUrl;
	
	@NotNull
	private Integer price;
	
	@NotNull
	private Integer stock;
	
	private String description;
	
	
	public ProductRequest(String productName, ProductCategory category, MultipartFile imageUrl, Integer price,
			Integer stock, String description) {
		
		this.productName = productName;
		this.category = category;
		this.imageUrl = imageUrl;
		this.price = price;
		this.stock = stock;
		this.description = description;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public ProductCategory getcategory() {
		return category;
	}
	public void setcategory(ProductCategory category) {
		this.category = category;
	}
	public MultipartFile getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(MultipartFile imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}

package com.example.demo.model;

import java.util.Date;

import com.example.demo.constent.ProductCategory;

public class Product {
	
	private String productName;
	private ProductCategory categery;
	private Integer productId;
	private String imageUrl;
	private Integer price;
	private Integer stock;
	private String description;
	private Date createdDate;
	private Date lastModifiedDate;
	
	 
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public ProductCategory getCategery() {
		return categery;
	}
	public void setCategery(ProductCategory categery) {
		this.categery = categery;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getstock() {
		return stock;
	}
	public void setstock(Integer stock) {
		this.stock = stock;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	

}

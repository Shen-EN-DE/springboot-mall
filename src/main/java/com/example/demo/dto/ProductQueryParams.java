package com.example.demo.dto;

import com.example.demo.constent.ProductCategory;

public class ProductQueryParams {
	
	private ProductCategory category;
	private String search;
	
	
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
	

}

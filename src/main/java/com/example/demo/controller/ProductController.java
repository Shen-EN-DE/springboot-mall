package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.constent.ProductCategory;
import com.example.demo.dto.ProductQueryParams;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.util.Page;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Validated
@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/public/products")
	public ResponseEntity<Page<Product>> getProduct(
			@RequestParam(required = false) ProductCategory category,
			@RequestParam(required = false) String search,
			
			@RequestParam(defaultValue = "created_date") String orderBy,
			@RequestParam(defaultValue = "desc") String sort,
			
			@RequestParam(defaultValue = "100") @Max(1000) @Min(0) Integer limit, // 使用max和min時要去上面使用@Validated
			@RequestParam(defaultValue = "0") @Min(0) Integer offset //從第幾筆開始抓取
	){
		ProductQueryParams productQueryParams = new ProductQueryParams();
		productQueryParams.setCategory(category);
		productQueryParams.setSearch(search);
		productQueryParams.setOrderBy(orderBy);
		productQueryParams.setSort(sort);
		productQueryParams.setLimit(limit);
		productQueryParams.setOffset(offset);
		
		
		//取得product list
		List<Product> productList = productService.getProducts(productQueryParams);
		
		//取得product總數   //因為前端如果想找某些類別種類的總參數，total不一樣，因此要用前端傳的資訊提供
		Integer total = productService.countProduct(productQueryParams); 
		
		Page<Product> page = new Page<>();
		page.setLimit(limit);
		page.setOffset(offset);
		page.setTotal(total);
		page.setResult(productList);
		
		return ResponseEntity.status(HttpStatus.OK).body(page);
		
	}
	
	
	@GetMapping("/public/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
		Product product = productService.getProductById(productId);
		
		if(product != null) {
			return ResponseEntity.status(HttpStatus.OK).body(product);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}	
	}
	
	@GetMapping("/public/products/types")
	public ResponseEntity<List<ProductCategory>> getProductTypes(){
		List<ProductCategory> productTypes = Arrays.asList(ProductCategory.values());
		
		if(!productTypes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(productTypes);
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@PostMapping("/admin/products")
	public ResponseEntity<Product> createProduct(
	        @RequestParam("productName") String productName,
	        @RequestParam("category") ProductCategory category,
	        @RequestParam(value = "imageUrl", required = false) MultipartFile imageUrl,
	        @RequestParam("price") Integer price,
	        @RequestParam("stock") Integer stock,
	        @RequestParam(value= "description", required = false) String description) {
		
		ProductRequest productRequest = new ProductRequest(productName, category, imageUrl, price, stock, description);
		
		Integer productId = productService.createProduct(productRequest);
		
		Product product = productService.getProductById(productId);
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}
	
	
	@PutMapping("/admin/products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
									 	 		 @RequestParam("productName") String productName,
										         @RequestParam("category") ProductCategory category,
										         @RequestParam(value = "imageUrl", required = false) MultipartFile imageUrl,
										         @RequestParam("price") Integer price,
										         @RequestParam("stock") Integer stock,
										         @RequestParam(value = "description", required = false) String description) {
		
		//check if product
		Product existProduct = productService.getProductById(productId); 
		
		if(existProduct == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}	
		

		if (imageUrl == null || imageUrl.isEmpty()) {
			System.out.println(existProduct.getImageUrl());
			productService.updateProductWithExistingImage(productId, productName, category, existProduct.getImageUrl(), price, stock, description);
			
		} else {
			ProductRequest productRequest = new ProductRequest(productName, category, imageUrl, price, stock, description);
			// update product data
			productService.updateProduct(productId, productRequest);
		}

		
		Product updateProduct = productService.getProductById(productId);
		
		return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
	}
	
	@DeleteMapping("/admin/products/{productId}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId){
		
		productService.deleteProductById(productId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}

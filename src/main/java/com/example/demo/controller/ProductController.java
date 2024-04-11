package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> getProduct(
			@RequestParam(required = false) ProductCategory category,
			@RequestParam(required = false) String search,
			
			@RequestParam(defaultValue = "created_date") String orderBy,
			@RequestParam(defaultValue = "desc") String sort,
			
			@RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit, // 使用max和min時要去上面使用@Validated
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
	
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
		Product product = productService.getProductById(productId);
		
		if(product != null) {
			return ResponseEntity.status(HttpStatus.OK).body(product);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}	
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {
		
		Integer productId = productService.createProduct(productRequest);
		
		Product product = productService.getProductById(productId);
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}
	
	
	@PutMapping("/products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
												 @RequestBody @Valid ProductRequest productRequest) {
		
		// check product is exist?
		Product product = productService.getProductById(productId);
		
		if(product == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}					
		
		// update product data
		productService.updateProduct(productId, productRequest);
		
		Product updateProduct = productService.getProductById(productId);
		
		return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
	}
	
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId){
		
		productService.deleteProductById(productId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}

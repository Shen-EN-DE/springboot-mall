package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.constent.ProductCategory;
import com.example.demo.dto.ProductQueryParams;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;

@Component
public interface ProductDao {
	Product getProductById(Integer productId);
	
	List<Product> getProducts(ProductQueryParams productQueryParams);
	
	Integer countProduct(ProductQueryParams productQueryParams);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId, ProductRequest productRequest);
	void updateProductWithExistingImage(Integer productId, Product product);
	
	void deleteProductById(Integer productId);
<<<<<<< HEAD

	void updateStock(Integer productId, Integer stock);
=======
	
	void updateStock (Integer productId, Integer stock);
>>>>>>> 5bc9e6873c2ae4fb5b8bf3df83b438ae145219ee

}

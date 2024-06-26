package com.example.demo.dao.impl;

import java.io.IOException;
import java.security.PublicKey;
import java.security.spec.NamedParameterSpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.constent.ProductCategory;
import com.example.demo.dao.ProductDao;
import com.example.demo.dto.ProductQueryParams;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.rowmapper.ProductRowMapper;
import com.example.demo.service.ProductService;

@Component
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	@Override
	public void updateStock(Integer productId, Integer stock) {
		String sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate"
				+ " WHERE product_id = :productId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("stock", stock);
		map.put("lastModifiedDate", new Date());
		map.put("productId", productId);
		
		namedParameterJdbcTemplate.update(sql, map); 
	}
	
	@Override
	public Product getProductById(Integer productId) {
		
		String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date "
				+ "FROM product "
				+ "WHERE product_id = :productId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		
		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
		if(productList.size() > 0) {
			return productList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public Integer countProduct(ProductQueryParams productQueryParams) {
		String sql = "SELECT count(*) FROM product WHERE 1=1";
		
		Map<String, Object> map = new HashMap<>();
		
		//查詢條件
		sql = addFilteringSql(sql, map, productQueryParams);
		
		Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
		
		return total;
	}


	@Override
	public List<Product> getProducts(ProductQueryParams productQueryParams) {
		
		String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date "
				+ "FROM product "
				+ "WHERE 1=1";
		
		Map<String, Object> map = new HashMap<>();
		
		//查詢條件
		sql = addFilteringSql(sql, map, productQueryParams);

		
		
		//排序  //不能用map.put方式拼接，只能打出來
		sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();
		
		//分頁
		sql = sql + " LIMIT :limit OFFSET :offset";
		map.put("limit", productQueryParams.getLimit());
		map.put("offset", productQueryParams.getOffset());
		
		
		List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
	  
		return productList;
	}


	@Override
	public Integer createProduct(ProductRequest productRequest) {
		String sql = "INSERT INTO product(product_name, category, image_url, price, stock, description, created_date, last_modified_date) "
				+ "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate)";
		
		Map<String, Object> map = new HashMap<>();
		map.put("productName", productRequest.getProductName());
		map.put("category", productRequest.getcategory().toString());
		
		byte[] imageBytes = null;
		try {
			imageBytes = productRequest.getImageUrl().getBytes();
			String base64Image = Base64.getEncoder().encodeToString(imageBytes); 
			
			map.put("imageUrl", base64Image);
			map.put("price", productRequest.getPrice());
			map.put("stock", productRequest.getStock());
			map.put("description", productRequest.getDescription());
			
			Date now = new Date();
			map.put("createdDate", now);
			map.put("lastModifiedDate", now);
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
			
			int productId = keyHolder.getKey().intValue();
			
			return productId;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl"
				+ ", price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate"
				+ " WHERE product_id = :productId ";

		byte[] imageBytes = null;
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("productId", productId);
			
			map.put("productName", productRequest.getProductName());
			map.put("category", productRequest.getcategory().toString());
			
			imageBytes = productRequest.getImageUrl().getBytes();
			String base64Images = Base64.getEncoder().encodeToString(imageBytes);
			
			map.put("imageUrl", base64Images);
			map.put("price", productRequest.getPrice());
			map.put("stock", productRequest.getStock());
			map.put("description", productRequest.getDescription());
			
			Date now = new Date();
			map.put("lastModifiedDate", now);
			
			namedParameterJdbcTemplate.update(sql, map);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void updateProductWithExistingImage(Integer productId, Product product) {
		String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl"
				+ ", price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate"
				+ " WHERE product_id = :productId ";

		byte[] imageBytes = null;
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		
		map.put("productName", product.getProductName());
		map.put("category", product.getCategory().toString());
		
		map.put("imageUrl", product.getImageUrl());
		map.put("price", product.getPrice());
		map.put("stock", product.getstock());
		map.put("description", product.getDescription());
		
		Date now = new Date();
		map.put("lastModifiedDate", now);
		
		namedParameterJdbcTemplate.update(sql, map);
		
	}

	
	
	
	@Override
	public void updateStock(Integer productId, Integer stock) {
		
		String sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate"
					+ " WHERE product_id = :productId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("stock", stock);
		map.put("productId", productId);
		map.put("lastModifiedDate", new Date());
		
		namedParameterJdbcTemplate.update(sql, map);
		
		
	}

	
	@Override
	public void deleteProductById(Integer productId) {
		String sql = "DELETE FROM product WHERE product_id = :productId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		
		namedParameterJdbcTemplate.update(sql, map);
		
	}

	
	private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams) {
		//查詢條件
		if(productQueryParams.getCategory() != null) {
			sql = sql + " AND category = :category";
			map.put("category", productQueryParams.getCategory().name());
		}
		
		if(productQueryParams.getSearch() != null) {
			sql = sql + " AND product_name LIKE :search";
			map.put("search", "%" + productQueryParams.getSearch() + "%");
		}
		
		return sql;
	}
	
}

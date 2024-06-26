package com.example.demo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale.Category;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.constent.ProductCategory;
import com.example.demo.model.Product;

public class ProductRowMapper implements RowMapper<Product>{

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Product product = new Product();
		
		product.setProductId(rs.getInt("product_id"));
		product.setProductName(rs.getString("product_name"));
		
//		String categoryStr = rs.getString("category");
//		ProductCategory category = ProductCategory.valueOf(categoryStr);
//		product.setCategery(category);
		product.setCategery(ProductCategory.valueOf(rs.getString("category")));
		
		product.setImageUrl(rs.getString("image_url"));
		product.setPrice(rs.getInt("price"));
		product.setstock(rs.getInt("stock"));
		product.setDescription(rs.getString("description"));
		product.setCreatedDate(rs.getTimestamp("created_date"));
		product.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
		
		
		
		return product;
	}

}

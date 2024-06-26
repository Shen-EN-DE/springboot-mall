package com.example.demo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.constent.UserAuthority;
import com.example.demo.model.User;

public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		User user = new User();
		
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setCreatedDate(rs.getTimestamp("created_date"));
		user.setLastModifiedDate(rs.getTimestamp("last_modified_date"));;
		user.setUserId(rs.getInt("user_id"));
		
		String authorityString = rs.getString("roles");
		if (authorityString != null) {
			user.setAuthority(UserAuthority.valueOf(authorityString));
		}
		
		
		return user;
	}
	
	

}

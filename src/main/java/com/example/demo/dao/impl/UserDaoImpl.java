package com.example.demo.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.hibernate.validator.cfg.context.ReturnValueConstraintMappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.SqlArrayValue;
import org.springframework.stereotype.Component;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;
import com.example.demo.rowmapper.UserRowMapper;


@Component
public class UserDaoImpl implements UserDao{
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	@Override
	public Integer createUser(UserRegisterRequest userRegisterRequest) {
		String sql = "INSERT INTO user(email, password, created_date, last_modified_date)"
				+ " VALUE (:email, :password, :createdDate, :lastMadifiedDate)";
		
		Map<String , Object> map = new HashMap<>();
		map.put("email", userRegisterRequest.getEmail());
		map.put("password", userRegisterRequest.getPassword());
		
		Date now = new Date();
		map.put("createdDate", now);
		map.put("lastMadifiedDate", now);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
		
		int userId = keyHolder.getKey().intValue();
		
		return userId;
		
	}

	@Override
	public User getUserById(Integer userId) {
		
		String sql =  "SELECT user_id, email, password, created_date, last_modified_date"
					+ " FROM user"
					+ " WHERE user_id = :userId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		
		
		List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
		
		if (userList.size() > 0) {
			return userList.get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public User getUserByEmail(String email) {
		String sql = "SELECT user_id, email, password, created_date, last_modified_date"
					+ " FROM user"
					+ " WHERE email = :email";
		
		Map<String, Object> map = new HashMap<>();
		map.put("email", email);
		
		List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
		
		if (userList.size() > 0) {
			return userList.get(0);
		}else {
			return null;
		}
		
	}
	
	
	

}
package com.example.demo.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.AuthDao;
import com.example.demo.dto.AuthRequest;



@Component
public class AuthDaoImpl implements AuthDao{
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Transactional
	@Override
	public void updateUserRole(Integer userId, AuthRequest authRequest) {
		String sql = "UPDATE user SET roles = :roles"
				+ " WHERE user_id = :userId ";
				
		Map<String, Object> map = new HashMap<>();
		map.put("roles", authRequest.getRole().toString());
		map.put("userId", userId);
		

		namedParameterJdbcTemplate.update(sql, map);

	}
	
	

}

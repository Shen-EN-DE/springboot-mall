package com.example.demo.dao;

import org.springframework.stereotype.Component;

import com.example.demo.dto.AuthRequest;

@Component
public interface AuthDao {
	
	void updateUserRole(Integer userId, AuthRequest authRequest);

}

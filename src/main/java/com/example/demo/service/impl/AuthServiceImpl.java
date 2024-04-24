package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.demo.dao.AuthDao;
import com.example.demo.dto.AuthRequest;
import com.example.demo.service.AuthService;

@Component
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	AuthDao authDao;

	@Override
	public void updateUserRole(Integer userId, AuthRequest authRequest) {
		
		authDao.updateUserRole(userId, authRequest);
	
	}
	
	
	
	

}

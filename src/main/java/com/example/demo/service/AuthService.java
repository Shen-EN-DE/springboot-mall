package com.example.demo.service;

import org.springframework.stereotype.Component;

import com.example.demo.dto.AuthRequest;

@Component
public interface AuthService {
	
	void updateUserRole(Integer userId, AuthRequest authRequest);

}

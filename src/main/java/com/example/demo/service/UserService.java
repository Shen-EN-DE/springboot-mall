package com.example.demo.service;

import org.springframework.stereotype.Component;

import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;

@Component
public interface UserService {
	
	Integer register(UserRegisterRequest userRegisterRequest);
	
	User getUserById(Integer userId);

}

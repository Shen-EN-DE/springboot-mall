package com.example.demo.dao;

import org.springframework.stereotype.Component;

import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;

@Component
public interface UserDao {
	
	Integer createUser(UserRegisterRequest userRegisterRequest);
	
	User getUserById(Integer userId);
	
	User getUserByEmail(String email);

}

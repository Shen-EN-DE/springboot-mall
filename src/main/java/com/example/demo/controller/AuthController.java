package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;
	
	@PutMapping("/admin/updateRole/{userId}")
	public ResponseEntity<User> updateUserRole(@PathVariable Integer userId,
												 @RequestBody @Valid AuthRequest authRequest) {
		
		// check user is exist?
		User user= userService.getUserById(userId);
		
		if(user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}					
		
		// update user data
		authService.updateUserRole(userId, authRequest);
		
		User updateUserRole = userService.getUserById(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(updateUserRole);
	}
}

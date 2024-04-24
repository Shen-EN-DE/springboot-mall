package com.example.demo.dto;

import java.util.Date;

import com.example.demo.constent.UserAuthority;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank; //不能為空和null
import jakarta.validation.constraints.NotNull; //不能為null

public class UserRegisterRequest {
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	private UserAuthority authority;
	
	
	
	public UserAuthority getAuthority() {
		return authority;
	}
	public void setAuthority(UserAuthority authority) {
		this.authority = authority;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	
	

}

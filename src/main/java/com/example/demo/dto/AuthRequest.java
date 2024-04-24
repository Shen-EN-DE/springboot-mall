package com.example.demo.dto;

import com.example.demo.constent.UserAuthority;

import jakarta.validation.constraints.NotNull;

public class AuthRequest {
	
	@NotNull(message = "Role is require")
	private UserAuthority role;

	public UserAuthority getRole() {
		return role;
	}

	public void setRole(UserAuthority role) {
		this.role = role;
	}
	
	

}

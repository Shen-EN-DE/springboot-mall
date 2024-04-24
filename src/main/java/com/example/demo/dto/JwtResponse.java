package com.example.demo.dto;

import java.util.List;

public class JwtResponse {
	
	private Integer id;
	private String email;
	private String token;
	private String type = "Bearer";
	
	private List<String> roles;
	
	public JwtResponse(Integer id, String email, String jwt, List<String> roles) {
		this.id = id;
		this.email = email;
		this.token = jwt;
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	

}

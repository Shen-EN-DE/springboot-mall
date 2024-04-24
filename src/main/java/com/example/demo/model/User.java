	package com.example.demo.model;

import java.time.LocalDate;
import java.util.Date;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import com.example.demo.constent.UserAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;

public class User {
	private Integer userId;
	
	@JsonProperty("e_mail")
	private String email;
	
	@JsonIgnore
	private String password;
	private Date createdDate;
	private Date lastModifiedDate;
	
	private UserAuthority authority;
	
	
	
	public UserAuthority getAuthority() {
		return authority;
	}
	public void setAuthority(UserAuthority authority) {
		this.authority = authority;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
	
	

}

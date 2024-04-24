package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.constent.UserAuthority;

public class LoginResponse {
	private String accessToken;
	private String refreshToken;
	
	private Integer userId;
	private String email;
	private UserAuthority userAuthority;
	private boolean premium;
	private LocalDate trailExpiration;
	
	
	
	
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
	public UserAuthority getUserAuthority() {
		return userAuthority;
	}
	public void setUserAuthority(UserAuthority userAuthority) {
		this.userAuthority = userAuthority;
	}
	public boolean isPremium() {
		return premium;
	}
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	public LocalDate getTrailExpiration() {
		return trailExpiration;
	}
	public void setTrailExpiration(LocalDate trailExpiration) {
		this.trailExpiration = trailExpiration;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	

}

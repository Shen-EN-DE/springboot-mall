package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.user.AppUserDetails;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping("/user/register")
	public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
		
		Integer userId =  userService.register(userRegisterRequest);
		
		User user = userService.getUserById(userId);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
		
	}
	
	@PostMapping("user/login")
	public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
//	public ResponseEntity<LoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
		Authentication authentication = 
				authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							userLoginRequest.getEmail(), userLoginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtTokenForUser(authentication);
		
		AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

		List<String> roles = userDetails.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority).toList();
		
		return ResponseEntity.ok(new JwtResponse(
				userDetails.getUserId(),
				userDetails.getEmail(),
				jwt,
				roles));
//		User user = userService.login(userLoginRequest);
//		String token = tokenServer.createToken(user); 
//		LoginResponse responose = tokenServer.createToken(userLoginRequest);
		
//		return ResponseEntity.status(HttpStatus.OK).body(user);
//		return ResponseEntity.ok(responose);
	}
	
//	@GetMapping("/parse-token")
//	public ResponseEntity<Map<String, Object>> parseToken(@RequestHeader(HttpHeaders.AUTHORIZATION)String authorization){
//		Map<String, Object> jwtPayload = tokenServer.parseToken(authorization);
//		return ResponseEntity.ok(jwtPayload);
}


package com.example.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.constent.UserAuthority;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserRegisterRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Component
public class UserServiceImpl implements UserService{
	
	private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Integer register(UserRegisterRequest userRegisterRequest) {
		
		
		User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
		
		if (user != null) {
			log.warn("email {} is exists", userRegisterRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
				
////		 MD5生成密碼
//		String hashPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
//		userRegisterRequest.setPassword(hashPassword);
		
		String encodePassword = passwordEncoder.encode(userRegisterRequest.getPassword());
		userRegisterRequest.setPassword(encodePassword);
		
		return userDao.createUser(userRegisterRequest);
		
	}
	

	@Override
	public User login(UserLoginRequest userLoginRequest) {
		
		User user = userDao.getUserByEmail(userLoginRequest.getEmail());
		if (user == null) {
			log.warn("email {} isn't regist", userLoginRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
			return user;
		}else {
			log.warn("Incorrect password for email {}", userLoginRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is incorrect");
		}
//		
		
//		//md5生成密碼
//		String hashPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
//		
//		if (user.getPassword().equals(hashPassword)) {
//			return user;
//		}else {
//			log.warn("the email {} is password incorrect", userLoginRequest.getEmail());
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST); 
//		}
	
	}
 

	@Override
	public User getUserById(Integer userId) {
		
		return userDao.getUserById(userId);
		
	}
	
	

}

package com.example.demo.security.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import com.example.demo.dao.UserDao;

//身份認證才可存取
@Component
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.example.demo.model.User user = userDao.getUserByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return AppUserDetails.buildUserDetails(user);
	}
}

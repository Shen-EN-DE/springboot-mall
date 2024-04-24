package com.example.demo.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.security.user.AppUserDetails;
import com.example.demo.security.user.UserDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	private static final Logger log = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)throws ServletException, IOException {
		
		try {
			String  jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validatedToken(jwt)) {
				String emall = jwtUtils.getUserNameFromToken(jwt);
				AppUserDetails userDetails = (AppUserDetails) userDetailServiceImpl.loadUserByUsername(emall);
				var authentication = 
						new UsernamePasswordAuthenticationToken(userDetails,  null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
		} catch (Exception e) {
			log.error("Cannot set uesr authentication: {} ", e.getMessage());
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7); //前面會友bearer 的資訊
		}
		return null;
	}
	
	
	
}

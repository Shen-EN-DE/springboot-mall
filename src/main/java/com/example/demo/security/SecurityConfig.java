package com.example.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.demo.security.jwt.AuthTokenFilter;
import com.example.demo.security.jwt.JwtAuthEntryPoint;
import com.example.demo.security.user.UserDetailServiceImpl;


@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@Autowired
	private JwtAuthEntryPoint jwtAuthEntryPoint;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthTokenFilter authenticationTokenFilter() {
		
		return new AuthTokenFilter();
	}
	
	//定義如何根據用戶提供的憑證來檢查用戶身份。
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		var authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailServiceImpl);
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	//利用如DaoAuthenticationProvider，它會使用你配置的UserDetailsService來獲取用戶詳細信息並進行密碼比對。
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 允許從這些源進行跨域請求
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration); // 所有請求都允許跨域
	    return source;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer :: disable)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.exceptionHandling(
					exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(registry  -> registry 
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.requestMatchers("/user/**").permitAll()
					.requestMatchers("/users/**").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/public/**").permitAll()
					.anyRequest().authenticated());
		http.authenticationProvider(authenticationProvider());
		//在處理鏈中加入自定義過濾器，例如JWT認證過濾器。
		http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}

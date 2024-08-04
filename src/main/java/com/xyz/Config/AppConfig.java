package com.xyz.Config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests().
		requestMatchers(HttpMethod.POST, "/signUp").permitAll()
		.anyRequest().authenticated().and().addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
		.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class).csrf().disable()
		.cors(cors->cors.configurationSource(corsConfigurationSource()))
		.formLogin().and().httpBasic();
		
		return httpSecurity.build();
		
	}
	
	private CorsConfigurationSource corsConfigurationSource() {
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration ccf = new CorsConfiguration();
				ccf.setAllowedOrigins(Arrays.asList(
						"http://localhost:3000/",
						"https://sankha-instagram-fullstack.vercel.app"));
				ccf.setAllowedMethods(Collections.singletonList("*"));
				ccf.setAllowCredentials(true);
				ccf.setAllowedHeaders(Collections.singletonList("*"));
				ccf.setExposedHeaders(Arrays.asList("Authorization"));
				ccf.setMaxAge(3600L);
				
				return ccf;
			}
		};
	}
	
	
	
	@Bean
	public PasswordEncoder getPassencode() {
		return new BCryptPasswordEncoder();
	}

}

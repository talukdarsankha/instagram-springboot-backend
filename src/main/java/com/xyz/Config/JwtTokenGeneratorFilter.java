package com.xyz.Config;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication!=null) {
			SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());
			String jwt = Jwts.builder()
					      .setIssuer("instragram-clone-two")
					      .setIssuedAt(new Date())
					      .claim("email", authentication.getName()) 
					      .claim("authorities", populateAuthorities(authentication.getAuthorities()))
					      .setExpiration(new Date(new Date().getTime()+360000000))
					      .signWith(key).compact();
			
			response.setHeader(JwtConstants.JWT_HEADER, jwt);
					      
			   
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	
	public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authority = new HashSet<>();
		for(GrantedAuthority e: collection) {
			authority.add(e.getAuthority());
		}
		
		return String.join(",", authority);
	}
	
	protected boolean shouldNotFilter(HttpServletRequest req) throws ServletException {
		return !req.getServletPath().equals("/signin");
	}

}

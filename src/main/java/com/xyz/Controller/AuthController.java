package com.xyz.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Exception.UserException;
import com.xyz.Models.User;
import com.xyz.service.UserService;

@RestController
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signUp")
	public ResponseEntity<User> signUp(@RequestBody User user) throws UserException{
		return new ResponseEntity<User>(userService.registerUser(user),HttpStatus.CREATED);                
	}
	
	@GetMapping("/signin")
	public ResponseEntity<User> signin(Authentication authentication) throws UserException{

		User user = userService.findUserByEmail(authentication.getName());
		if(user!=null) {
			return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
		}
		throw new BadCredentialsException("Invalid Email Address...");
		
	}
	
	
	

}

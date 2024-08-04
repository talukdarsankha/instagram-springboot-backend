package com.xyz.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Dto.UserDto;
import com.xyz.Exception.UserException;
import com.xyz.Models.Post;
import com.xyz.Models.User;
import com.xyz.Response.MessageResponse;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/req")
	private ResponseEntity<User> getUserfromToken(@RequestHeader("Authorization") String jwt) throws UserException {
		
		return new ResponseEntity<User>(userService.findUserByJwt(jwt),HttpStatus.OK);
		
	}
	
	@PutMapping("/follow/{followUserId}")
	public ResponseEntity<MessageResponse> followUser(@RequestHeader("Authorization") String jwt,@PathVariable("followUserId") Integer followUserId ) throws UserException{
		User reqUser = userService.findUserByJwt(jwt);
		User followUser = userService.findUserById(followUserId);
	    String message = userService.followUser(reqUser, followUser);
		return new ResponseEntity<MessageResponse>(new MessageResponse(message),HttpStatus.OK);
	
	}
	
	
	@PutMapping("/unfollow/{unfollowUserId}")
	public ResponseEntity<MessageResponse> unfollowUser(@RequestHeader("Authorization") String jwt,@PathVariable("unfollowUserId") Integer unfollowUserId ) throws UserException{
		User reqUser = userService.findUserByJwt(jwt);
		User unfollowUser = userService.findUserById(unfollowUserId);
	    String message = userService.unFollowUser(reqUser, unfollowUser);
		return new ResponseEntity<MessageResponse>(new MessageResponse(message),HttpStatus.OK);
	
	}
	
	
	   // this api create both profile update and profilePhoto update
	@PutMapping("/account/edit")
	public ResponseEntity<User> updatePhoto(@RequestHeader("Authorization") String jwt, @RequestBody User user) throws UserException{
		User reqUser= userService.findUserByJwt(jwt);
		return new ResponseEntity<User>(userService.updateReqUser(reqUser, user) ,HttpStatus.OK);
	}

	
	 
		@GetMapping("/getUser/{username}")
		public ResponseEntity<User> getByusername(@PathVariable("username") String username) throws UserException{
			return new ResponseEntity<User>(userService.findUserByUsername(username),HttpStatus.OK) ;
		}
		
		@GetMapping("/search")
		public ResponseEntity<List<User>> seachUser(@RequestParam("query") String query){
			return new ResponseEntity<List<User>>(userService.searchUser(query),HttpStatus.OK);
		}
		
		@PutMapping("/savepost/{postId}")
		public ResponseEntity<User> savePost(@RequestHeader("Authorization") String jwt, @PathVariable("postId") Integer postId) throws UserException {
			User user = userService.findUserByJwt(jwt);
			return new ResponseEntity<User>(userService.savePost(user, postId),HttpStatus.OK);
		}
		
		@PutMapping("/unsavepost/{postId}")
		public ResponseEntity<User> unsavePost(@RequestHeader("Authorization") String jwt, @PathVariable("postId") Integer postId) throws UserException {
			User user = userService.findUserByJwt(jwt);
			return new ResponseEntity<User>(userService.unsavePost(user, postId),HttpStatus.OK);
		}
	
		
		

}

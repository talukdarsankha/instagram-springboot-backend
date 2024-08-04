package com.xyz.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.Exception.UserException;
import com.xyz.Models.Comment;
import com.xyz.Models.Post;
import com.xyz.Models.User;
import com.xyz.service.CommentService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
    
	@PostMapping("/post/{postId}")
	public ResponseEntity<Comment> createPostComment(@RequestHeader("Authorization") String jwt, @RequestBody Comment comment, @PathVariable("postId") Integer postId) throws UserException{
		User user  = userService.findUserByJwt(jwt);
		return new ResponseEntity<Comment>(commentService.createComment(user, comment, postId),HttpStatus.CREATED);
		
	}
	
	@PutMapping("/like/{commentId}")
	public ResponseEntity<Comment> likeComment(@RequestHeader("Authorization") String jwt, @PathVariable("commentId") Integer commentId) throws UserException{
		User user = userService.findUserByJwt(jwt);
		
		return new ResponseEntity<Comment>(commentService.likeComment(user, commentId), HttpStatus.OK);
	}
	
	@PutMapping("/unlike/{commentId}")
	public ResponseEntity<Comment> unlikeComment(@RequestHeader("Authorization") String jwt, @PathVariable("commentId") Integer commentId) throws UserException{
		User user = userService.findUserByJwt(jwt);
		
		return new ResponseEntity<Comment>(commentService.likeComment(user, commentId), HttpStatus.OK);
	}
}

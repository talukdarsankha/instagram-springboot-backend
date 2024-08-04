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

import com.xyz.Exception.PostException;
import com.xyz.Exception.UserException;
import com.xyz.Models.Post;
import com.xyz.Models.User;
import com.xyz.service.PostService;
import com.xyz.service.UserService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@PostMapping("/create")
	public ResponseEntity<Post> createPost(@RequestHeader("Authorization")String jwt , @RequestBody Post post) throws UserException{
		User reqUser = userService.findUserByJwt(jwt);
		Post createPost = postService.createPost(post, reqUser);
		
		return new ResponseEntity<Post>(createPost,HttpStatus.CREATED);		
	}
	
	@GetMapping("/followUserPost/{followuserIdList}")  // in there req user id included
	public ResponseEntity<List<Post>> findAllFollowUsersPost(@RequestHeader("Authorization") String jwt,@PathVariable("followuserIdList") List<Integer> followuserIdList ) throws PostException{
		return new ResponseEntity<List<Post>>(postService.findAllFollowUsersPost(followuserIdList), HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/profileUserPosts/{profileId}")
	public ResponseEntity<List<Post>> profileUserAllPosts(@PathVariable("profileId") Integer profileId) throws UserException{
		User user = userService.findUserById(profileId);
		return new ResponseEntity<List<Post>>(postService.findAllProfileUserPosts(profileId),HttpStatus.OK);
	}
	
	@PutMapping("/like/{postId}")
	public ResponseEntity<Post> likePost(@RequestHeader("Authorization") String jwt, @PathVariable("postId") Integer postId ) throws UserException{
		User user = userService.findUserByJwt(jwt);
		return new ResponseEntity<Post>(postService.likePost(user, postId), HttpStatus.OK);
	}
	
	@PutMapping("/unlike/{postId}")
	public ResponseEntity<Post> unlikePost(@RequestHeader("Authorization") String jwt, @PathVariable("postId") Integer postId ) throws UserException{
		User user = userService.findUserByJwt(jwt);
		return new ResponseEntity<Post>(postService.unlikePost(user, postId), HttpStatus.OK);
	}
	
	@GetMapping("/getPost/{postId}")
	public ResponseEntity<Post> getPostById(@PathVariable("postId") Integer postId){
		return new ResponseEntity<Post>(postService.findPostById(postId),HttpStatus.OK);
	}
	
	
      
    }

package com.xyz.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.Dto.UserDto;
import com.xyz.Models.Comment;
import com.xyz.Models.Post;
import com.xyz.Models.User;
import com.xyz.Repository.CommentRepository;
import com.xyz.Repository.PostRepository;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public Comment createComment(User user, Comment comment, Integer postId) {
		UserDto commentUser = new UserDto();
		
		commentUser.setId(user.getId());
		commentUser.setEmail(user.getEmail());
		commentUser.setImage(user.getImage());
		commentUser.setName(user.getName());
		commentUser.setUsername(user.getUsername());
		
		comment.setCommentedUser(commentUser);
		comment.setCreatedAt(LocalDateTime.now());
		
		Comment createdComment = commentRepository.save(comment);
          // in post obj we need to store createdComment obj because it have id but createdComment has no id
		
		Post post = postService.findPostById(postId);
		post.getPostComment().add(createdComment);
		postRepository.save(post);
		
		return createdComment;
		
		
	}
	
	
	@Override
	public Comment findCommentById(Integer commentId) {
		return commentRepository.findById(commentId).get();
	}
	

	@Override
	public Comment likeComment(User user, Integer commentId) {
		UserDto likeUser = new UserDto();
		
		likeUser.setId(user.getId());
		likeUser.setEmail(user.getEmail());
		likeUser.setImage(user.getImage());
		likeUser.setName(user.getName());
		likeUser.setUsername(user.getUsername());
		
		Comment likeComment = findCommentById(commentId);
		
//		if(!likeComment.getCommentLikeUsers().contains(likeComment))
		
		likeComment.getCommentLikeUsers().add(likeUser);
		
		return commentRepository.save(likeComment);
		
	}


	@Override
	public Comment unlikeComment(User user, Integer commentId) {
       UserDto unlikeUser = new UserDto();
		
       unlikeUser.setId(user.getId());
       unlikeUser.setEmail(user.getEmail());
       unlikeUser.setImage(user.getImage());
		unlikeUser.setName(user.getName());
		unlikeUser.setUsername(user.getUsername());
		
		Comment unlikeComment = findCommentById(commentId);
		
		
		unlikeComment.getCommentLikeUsers().remove(unlikeUser);
		
		return commentRepository.save(unlikeComment);
		
	}



	


}

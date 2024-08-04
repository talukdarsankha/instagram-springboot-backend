package com.xyz.service;

import org.springframework.stereotype.Service;

import com.xyz.Dto.UserDto;
import com.xyz.Models.Comment;
import com.xyz.Models.User;


public interface CommentService {
   
	public Comment createComment(User user,Comment comment, Integer postId);
	
	public Comment findCommentById(Integer commentId);
	
	public Comment likeComment(User user, Integer commentId);
	
	public Comment unlikeComment(User user, Integer commentId);
}
  
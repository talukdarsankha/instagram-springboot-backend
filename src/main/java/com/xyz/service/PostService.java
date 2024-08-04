package com.xyz.service;

import java.util.List;

import com.xyz.Dto.UserDto;
import com.xyz.Exception.PostException;
import com.xyz.Models.Post;
import com.xyz.Models.User;

public interface PostService {
	
	public Post createPost(Post post,User user);
	
	public List<Post> findAllFollowUsersPost(List<Integer> followUserIds) throws PostException;

	public List<Post> findAllProfileUserPosts(Integer profileId);
	
	public Post likePost(User user,Integer postId);
	
	public Post unlikePost(User user,Integer postId);
	
	public Post findPostById(Integer postId);
}

package com.xyz.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.Dto.UserDto;
import com.xyz.Exception.PostException;
import com.xyz.Models.Post;
import com.xyz.Models.User;
import com.xyz.Repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepository;

	
	
	@Override
	public Post createPost(Post post, User user) {
        UserDto userDto = new UserDto();
		
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setImage(user.getImage());
		userDto.setName(user.getName());
		userDto.setUsername(user.getUsername());
		
		post.setUser(userDto);
		post.setCreatedAt(LocalDateTime.now());
		
		return postRepository.save(post);
	}

	
	@Override
	public Post findPostById(Integer postId) {
		return postRepository.findById(postId).get();
	}

	@Override
	public List<Post> findAllFollowUsersPost(List<Integer> followUserIds) throws PostException {
		// TODO Auto-generated method stub
		List<Post> allPosts = postRepository.findAllFollowUserPostByDateDesc(followUserIds);
		
		if (allPosts.size()==0) {
			throw new PostException("Post Not Found by this followerIdList");
		}
		return allPosts;
	}


	@Override
	public List<Post> findAllProfileUserPosts(Integer profileId) {
		 List<Post> allPosts = postRepository.findByUserId(profileId);
		 return allPosts;
	}


	@Override
	public Post likePost(User user, Integer postId) {
		UserDto likeUser = new UserDto();
		
		likeUser.setId(user.getId());
		likeUser.setEmail(user.getEmail());
		likeUser.setImage(user.getImage());
		likeUser.setName(user.getName());
		likeUser.setUsername(user.getUsername());
		
		Post likePost = postRepository.findById(postId).get();
		likePost.getLikedUsers().add(likeUser);
		
		return postRepository.save(likePost);
	}


	@Override
	public Post unlikePost(User user, Integer postId) {
        
		UserDto unlikeUser = new UserDto();
		
		unlikeUser.setId(user.getId());
		unlikeUser.setEmail(user.getEmail());
		unlikeUser.setImage(user.getImage());
		unlikeUser.setName(user.getName());
		unlikeUser.setUsername(user.getUsername());
		
		Post unlikePost = postRepository.findById(postId).get();
		unlikePost.getLikedUsers().remove(unlikeUser);
		
		return postRepository.save(unlikePost);
	}

	
	

	

}

package com.xyz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xyz.Config.JwtConstants;
import com.xyz.Dto.UserDto;
import com.xyz.Exception.UserException;
import com.xyz.Models.Post;
import com.xyz.Models.User;
import com.xyz.Repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private PostService postService;

	@Override
	public User registerUser(User user) throws UserException {
		
		if (user.getEmail()==null || user.getPassword()==null || user.getUsername()==null || user.getName()==null)  {
			throw new UserException("Email,Password,name,username can't be empty");			
		}
		
		Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());       
		if(isEmailExist.isPresent()) {
			throw new UserException("Email Already Exist........");
		}
		
		Optional<User> isUserNameExist = userRepository.findByUsername(user.getUsername());
		if(isUserNameExist.isPresent()) {
			throw new UserException("Username Already Exist........");
		}
		
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setName(user.getName());
		newUser.setUsername(user.getUsername());
		
		return userRepository.save(newUser);
		
	}

	@Override
	public User findUserByEmail(String email) throws UserException {
		Optional<User> opt = userRepository.findByEmail(email);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new UserException("User Not Found with this email :"+email);
	}

	@Override
	public User findUserByUsername(String username) throws UserException {
		Optional<User> opt = userRepository.findByUsername(username);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new UserException("User Not Found with this username :"+username);
	}
	
//	@Override
//	public User findUserByUsername(String username) {
//		return userRepository.findByUsername(username).get();
//		
//	}

	@Override
	public User findUserById(Integer userId) throws UserException {
		// TODO Auto-generated method stub
		Optional<User> opt = userRepository.findById(userId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new UserException("User Not Found with this id :"+userId);
	}

	@Override
	public User findUserByJwt(String jwt) throws UserException {
		// TODO Auto-generated method stub
		jwt = jwt.substring(7);
		
		Claims claims = Jwts.parserBuilder()
				        .setSigningKey(JwtConstants.SECRET_KEY.getBytes())
				        .build()
				        .parseClaimsJws(jwt)
				        .getBody();
		
		String email = (String) claims.get("email");
		return findUserByEmail(email);
				        
		
	}

	@Override
	public String followUser(User reqUser, User followUser) {
		// TODO Auto-generated method stub
		
		UserDto follower = new UserDto();
		
		follower.setId(reqUser.getId());
		follower.setEmail(reqUser.getEmail());
		follower.setImage(reqUser.getImage());
		follower.setName(reqUser.getName());
		follower.setUsername(reqUser.getUsername());
		
        UserDto followingUser = new UserDto();
		
        followingUser.setId(followUser.getId());
        followingUser.setEmail(followUser.getEmail());
        followingUser.setImage(followUser.getImage());
        followingUser.setName(followUser.getName());
        followingUser.setUsername(followUser.getUsername());
		
        reqUser.getFollowings().add(followingUser);
        followUser.getFollowers().add(follower);
        
        userRepository.save(reqUser);
        userRepository.save(followUser);
        
		return "You Are now following "+followUser.getName();
	}

	@Override
	public String unFollowUser(User reqUser, User unFollowUser) {
		
		 UserDto unfollowUser = new UserDto();
		 
		 unfollowUser.setId(reqUser.getId());
		 unfollowUser.setEmail(reqUser.getEmail());
		 unfollowUser.setImage(reqUser.getImage());
		 unfollowUser.setName(reqUser.getName());
		 unfollowUser.setUsername(reqUser.getUsername());
		 
         UserDto unfollowIngUser = new UserDto();
		 
         unfollowIngUser.setId(unFollowUser.getId());
         unfollowIngUser.setEmail(unFollowUser.getEmail());
         unfollowIngUser.setImage(unFollowUser.getImage());
         unfollowIngUser.setName(unFollowUser.getName());
         unfollowIngUser.setUsername(unFollowUser.getUsername());
         
         reqUser.getFollowings().remove(unfollowIngUser);
         unFollowUser.getFollowers().remove(unfollowUser);
         
         userRepository.save(reqUser);
         userRepository.save(unFollowUser);
         
         return "You Are now unfollowing "+unFollowUser.getName();
		 
	}

	@Override
	public User updateReqUser(User exitsUser, User updateUser) throws UserException {
		
		if (updateUser.getUsername()!=null) {
			exitsUser.setUsername(updateUser.getUsername());
		}
		
		if (updateUser.getEmail()!=null) {
			exitsUser.setEmail(updateUser.getEmail());
		}
		
		if (updateUser.getPhone()!=null) {
			exitsUser.setPhone(updateUser.getPhone());
		}
		if (updateUser.getPassword()!=null) {
			exitsUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
		}
		
		if (updateUser.getName()!=null) {
			exitsUser.setName(updateUser.getName());
		}
		
		if (updateUser.getMobile()!=null) {
			exitsUser.setMobile(updateUser.getMobile());
		}
		
		if (updateUser.getWebsite()!=null) {
			exitsUser.setWebsite(updateUser.getWebsite());
		}
		
		if (updateUser.getGender()!=null) {
			exitsUser.setGender(updateUser.getGender());
		}
		
		if (updateUser.getBio()!=null) {
			exitsUser.setBio(updateUser.getBio());
		}
		
		if (updateUser.getImage()!=null) {
			exitsUser.setImage(updateUser.getImage());
		}
		
		if (updateUser.getId().equals(exitsUser.getId())) {
			return userRepository.save(exitsUser);
		}
		
		throw new UserException("You can't update another user account.......");
	}

	@Override
	public List<User> searchUser(String searchQuery) {
		return userRepository.searchUser(searchQuery);
	}

	@Override
	public User savePost(User user, Integer postId) {
		Post savePost = postService.findPostById(postId);
		if(!user.getSavePosts().contains(savePost)) {
			user.getSavePosts().add(savePost);
			return userRepository.save(user);
		}
		return user;
		
	}

	@Override
	public User unsavePost(User user, Integer postId) {
		Post unsavePost = postService.findPostById(postId);
		if(user.getSavePosts().contains(unsavePost)) {
			user.getSavePosts().remove(unsavePost);
			return userRepository.save(user);
		}
		return user;
		
	}

	
	
	
	
	
}

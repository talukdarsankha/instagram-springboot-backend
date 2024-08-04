package com.xyz.service;

import java.util.List;

import com.xyz.Exception.UserException;
import com.xyz.Models.User;

public interface UserService {
	
    public User registerUser(User user) throws UserException;
    
    public User findUserByEmail(String email) throws UserException;
    
    public User findUserByUsername(String username) throws UserException;

    
    public User findUserById(Integer userId ) throws UserException;
    
    public User findUserByJwt(String jwt) throws UserException;
    
    public String followUser(User reqUser , User followUser);
    
    public String unFollowUser(User reqUser , User unFollowUser);
    
    public User updateReqUser(User exitsUser,User updateUser) throws UserException;

    public List<User> searchUser(String searchQuery);
    
    public User savePost(User user,Integer postId);

    public User unsavePost(User user,Integer postId);
    
   
    
}

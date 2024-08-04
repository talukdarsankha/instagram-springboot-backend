package com.xyz.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xyz.Models.Comment;
import com.xyz.Models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	
	@Query("SELECT p from Post p where p.user.id IN :userIds ORDER BY p.createdAt DESC")
	public List<Post> findAllFollowUserPostByDateDesc( List<Integer> userIds);

	
	public List<Post> findByUserId(Integer profileId); 
	
 
	
}

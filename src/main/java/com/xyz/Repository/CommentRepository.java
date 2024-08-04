package com.xyz.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xyz.Models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	
	
}

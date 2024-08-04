package com.xyz.Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xyz.Dto.UserDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String email;
	private String phone;
	private String password;
	private String name;
	private String mobile;
	private String website;
	private String gender;
	private String bio;
	private String image;
	
	
	@Embedded
	@ElementCollection
	private Set<UserDto> followers = new HashSet<>();
	
	@Embedded
	@ElementCollection
	private Set<UserDto> followings = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Story> stories = new ArrayList<>();
	
	@ManyToMany
	private List<Post> savePosts = new ArrayList<>();
	
	
	

}

package com.xyz.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	

	private Integer id;
	private String username;
	private String email;
	private String name;
	private String image;
	
	

}

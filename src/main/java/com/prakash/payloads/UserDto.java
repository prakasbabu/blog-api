package com.prakash.payloads;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UserDto {
	private int id;
	
	@NotEmpty
	@Size(min =4,message="Name must at least contain four characters..")
	private String name;
	
	@Email(message ="Email Address must contain @ symbol")
	private String email;
	@NotEmpty
	@Size(min=5,max=10, message ="Password must be between 5-10 characters")
	private String password;
	@NotEmpty(message="please provide few lines about yourself")
	private String about;

	
	
}

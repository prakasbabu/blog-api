package com.prakash.service;

import java.util.List;

import com.prakash.payloads.UserDto;


public interface UserService {
	
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List <UserDto> getAllUsers(); 
	void deleteUser(Integer userId);

}

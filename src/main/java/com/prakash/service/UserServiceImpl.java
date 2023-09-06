package com.prakash.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prakash.entity.User;
import com.prakash.exception.ResourceNotFoundException;
import com.prakash.payloads.UserDto;
import com.prakash.repository.UserRepository;
@Service
@Transactional
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto,User.class);
		
		return user;
	}
	
	public UserDto userToDto(User user) {
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
			User user = this.dtoToUser(userDto);
			User savedUser = this.userRepository.save(user); 
		
	return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user= this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepository.save(user);
	return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user= this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		
	return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
			List<User> users = this.userRepository.findAll();
			
			List <UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepository.findById(userId).orElseThrow((()-> new ResourceNotFoundException("User","id",userId)));
		
		this.userRepository.delete(user);
	}

}

package com.prakash.payloads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prakash.entity.User;
import com.prakash.exception.ResourceNotFoundException;
import com.prakash.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
	User user	= this.userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User ","email " +username, 0));
		
		
		return user;
	}

}

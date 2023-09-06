package com.prakash.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prakash.entity.User;

import net.bytebuddy.asm.Advice.This;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);

}

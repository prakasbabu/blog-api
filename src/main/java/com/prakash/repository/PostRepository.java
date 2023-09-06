package com.prakash.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prakash.entity.Category;
import com.prakash.entity.Post;
import com.prakash.entity.User;
import com.prakash.payloads.PostDto;

public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByCategory(Category cat);

	List<Post> findByUser(User user);

	Optional<Post> findByPostId(Integer postId);

	Page <Post> findAll(Pageable p);
	
	List <Post> findByTitleContaining(String title);

	

}

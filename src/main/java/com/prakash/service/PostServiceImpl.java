package com.prakash.service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prakash.entity.Category;
import com.prakash.entity.Post;
import com.prakash.entity.User;
import com.prakash.exception.ResourceNotFoundException;
import com.prakash.payloads.PostDto;
import com.prakash.payloads.PostResponse;
import com.prakash.repository.CategoryRepository;
import com.prakash.repository.PostRepository;
import com.prakash.repository.UserRepository;

import net.bytebuddy.asm.Advice.This;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
	@Override
	public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User ", "User ID", userId));
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category ","Category Id",categoryId));
		Post post = this.modelMapper.map(postDto, Post.class); 
		
	
	
		
		post.setPostedDate(new Date());
		post.setImageName("someImage.png");
		
		post.setCategory(category);
		post.setUser(user);
		
		postRepository.save(post);
		
		
		return this.modelMapper.map(post, PostDto.class);
	}



	@Override
	public List <PostDto> getPostByCategory(Integer categoryId) {
		
		Category cat = this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Category id",categoryId));
		
		
		List <Post> posts = postRepository.findByCategory(cat);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
		.collect(Collectors.toList());

			return postDtos;
	}



	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		
		
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ","User Id ",userId));
		
		  List<Post> posts =  this.postRepository.findByUser(user);
		  
		 List <PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
				 .collect(Collectors.toList());
		
		return postDtos;
	}



	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		
		Sort sort = ( sortDir.equalsIgnoreCase("asc"))? 
				sort = Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		
			
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page <Post> pagePost =  postRepository.findAll(p);
		
		List<Post> posts = pagePost.getContent();
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		
		return postResponse;
	}



	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post ","Post Id ",postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}



	@Override
	public void deletePost(Integer postId) {
		
		Post post= postRepository.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post ","Post Id ",postId));
		
		// Optional<Post> post = this.postRepository.findById(postId);
		//if result.isPresent(), delete result.get()
			postRepository.delete(post);
		System.out.println("It is not being deleted");
		
	}



	@Override
	public PostDto updatePost(PostDto postDto ,Integer postId) {
		Post post= this.postRepository.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post ","Post Id ",postId));
		
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		
		postRepository.save(post);
		
		return modelMapper.map(post, PostDto.class);
	}



	@Override
	public List <PostDto> searchPosts(String keyword) {
		
		List<Post> posts = this.postRepository.findByTitleContaining(keyword);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class))
				.collect(Collectors.toList());
		
		return postDtos;
	}


	
	
	
	
	

	
}

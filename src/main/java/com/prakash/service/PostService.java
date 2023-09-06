package com.prakash.service;

import java.util.List;

import com.prakash.payloads.PostDto;
import com.prakash.payloads.PostResponse;


public interface PostService {
	
	
	public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId );
	
	public List <PostDto> getPostByCategory(Integer categoryId) ;	
	
	public List <PostDto> getPostByUser(Integer userId) ;	
	
	
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	public PostDto getPostById(Integer postId);
	
	
	//delete post
	public void deletePost(Integer postId);
	
			
	//update post
	
	public PostDto updatePost(PostDto postDto,Integer postId);
	
	
	
	//search post
	
	
	public List <PostDto> searchPosts(String keyword);
	
	
	
	

}

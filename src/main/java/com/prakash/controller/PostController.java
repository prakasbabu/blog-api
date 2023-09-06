package com.prakash.controller;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prakash.payloads.ApiResponse;
import com.prakash.payloads.FileResponse;
import com.prakash.payloads.PostDto;
import com.prakash.payloads.PostResponse;
import com.prakash.service.FileService;
import com.prakash.service.PostService;
import com.prakash.utils.ApplicationConstant;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, 
			@PathVariable Integer userId,
			
			@PathVariable Integer categoryId){
		
		PostDto newPost = this.postService.createPost(postDto, categoryId, userId);
				
		return new ResponseEntity<PostDto>(newPost,HttpStatus.CREATED);
	}
	
	
	//getByUserCategory
	@GetMapping("/category/{categoryId}/post")
	
	ResponseEntity <List<PostDto>> getByUserCategory(@PathVariable Integer categoryId){
		
		List<PostDto> postDtos =  this.postService.getPostByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	
	}
	
	
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<List<PostDto>> getByUser(@PathVariable Integer userId){
		
		List<PostDto> postDtos = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value ="pageNumber",defaultValue = ApplicationConstant.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value ="pageSize",defaultValue = ApplicationConstant.PAGE_SIZE,required = false)Integer pageSize,
			@RequestParam(value ="sortBy",defaultValue = ApplicationConstant.SORT_BY,required = false)String sortBy,
			@RequestParam(value ="sortDir",defaultValue = ApplicationConstant.SORT_DIR,required = false)String sortDir
			){
		
		PostResponse allPost =this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		
		
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
	}
	
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity <PostDto> getPost(@PathVariable Integer postId){
		
		PostDto postDto = this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePostById(@PathVariable Integer postId) {
		
		postService.deletePost(postId);
		System.out.println(" from delete controller");
		
		return new ApiResponse("Post deleted with Post Id "+postId.toString(), true);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Integer postId){
		
		PostDto	updatedPostDto = postService.updatePost(postDto,postId);
		
			return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/posts/search/{keyword}")
	
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keyword){
				
		List<PostDto> postDtos =	this.postService.searchPosts(keyword);
		
		
		return new ResponseEntity<List<PostDto>>(postDtos, HttpStatus.OK);
		
	}

	
	@PostMapping("/file/upload")
	
	public ResponseEntity<FileResponse> filepload(@RequestParam("image")MultipartFile image) {
		
		
		
		String fileName = null;
		try {
			fileName = fileService.uploadImage(path, image);
		} catch (IOException e) {
			
			return new ResponseEntity<FileResponse>( new FileResponse(fileName," not uploaded "),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<FileResponse>( new FileResponse(fileName,"uploaded successfully"),HttpStatus.OK);
	}
	
	@GetMapping(value ="/images/{imageName}", produces =MediaType.IMAGE_JPEG_VALUE)
	public void downloadFile(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		
		 InputStream resource = this.fileService.getResource(path, imageName);
		 response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		 StreamUtils.copy(resource, response.getOutputStream());
		
	}
		
		@PostMapping("/post/image/upload/{postId}")
			public ResponseEntity<PostDto> imageUpload(@RequestParam("image")MultipartFile image,
													@PathVariable Integer postId ) throws IOException {
		
			PostDto postDto = this.postService.getPostById(postId);
			String	fileName = fileService.uploadImage(path, image);
			postDto.setImageName(fileName);
			PostDto updatedPost = this.postService.updatePost(postDto, postId);
						
			return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
		
	}
	
	
	
	
	
	
}

package com.prakash.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prakash.entity.Comment;
import com.prakash.entity.Post;
import com.prakash.exception.ResourceNotFoundException;
import com.prakash.payloads.CommentDto;
import com.prakash.repository.CommentRepo;
import com.prakash.repository.PostRepository;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepo commentRepo;
  
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
	Post post  = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post  ", " Post Id  ", postId));
	
	Comment comment = this.modelMapper.map(commentDto, Comment.class);
	
	comment.setPost(post);
	Comment savedComment = this.commentRepo.save(comment);
			
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment  ", " Comment Id  ", commentId));
		this.commentRepo.delete(comment);

	}

}

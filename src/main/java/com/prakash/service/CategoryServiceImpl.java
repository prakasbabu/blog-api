package com.prakash.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prakash.entity.Category;
import com.prakash.exception.ResourceNotFoundException;
import com.prakash.payloads.CategoryDto;
import com.prakash.repository.CategoryRepository;
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Autowired
	private ModelMapper modelMapper;

	
	public CategoryDto entityToDto(Category category) {
		
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		
		return categoryDto;
	}
	
	
	
	public Category dtoToEntity(CategoryDto categoryDto) {
		//Category category = this.modelMapper.map(categoryDto, Category.class);
		//return category;
		
		Category category = new Category();
		BeanUtils.copyProperties(categoryDto, category);
		return category;
		
	}
	
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category category = this.dtoToEntity(categoryDto);
				
		Category createdUser = categoryRepository.save(category);
		
		return this.entityToDto(createdUser);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto CategoryDto, Integer categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
		
		category.setCategoryDescription(CategoryDto.getCategoryDescription());
		category.setCategoryTitle(CategoryDto.getCategoryTitle());
		Category updatedCategory = categoryRepository.save(category);
		
		return this.entityToDto(updatedCategory);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
		
		
		return this.entityToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		
		List<Category> categories = this.categoryRepository.findAll();
		List<CategoryDto> catogoryDtos = categories.stream().map(category->this.entityToDto(category)).collect(Collectors.toList());
				
				
		return catogoryDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("User","User Id",categoryId));
		this.categoryRepository.deleteById(categoryId);
		
	}

}

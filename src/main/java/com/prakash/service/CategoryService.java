package com.prakash.service;

import java.util.List;

import com.prakash.payloads.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto CategoryDto, Integer userId);
	CategoryDto getCategoryById(Integer userId);
	List <CategoryDto> getAllCategory(); 
	void deleteCategory(Integer userId);

}

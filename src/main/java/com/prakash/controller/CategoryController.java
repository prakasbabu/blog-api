package com.prakash.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prakash.entity.Category;
import com.prakash.payloads.CategoryDto;
import com.prakash.payloads.UserDto;
import com.prakash.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired()
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
				
		 categoryDto = this.categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<>(categoryDto,HttpStatus.CREATED);
		
	}
	
	

	@GetMapping("/{categoryId}")
	
	public ResponseEntity<CategoryDto> getCategory( @PathVariable Integer categoryId){
			
	
		return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
				
	}
	
		@GetMapping("/")
		public ResponseEntity <List<CategoryDto>> getAllCategory(@RequestBody  CategoryDto categoryDto){
		
					
		return ResponseEntity.ok( this.categoryService.getAllCategory());
		
	}
	
		// update
		
		
		@PutMapping("/{categoryId}")
		public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
			
			CategoryDto updatedUser = this.categoryService.updateCategory(categoryDto, categoryId);
			
			return ResponseEntity.ok(updatedUser);
		
		}
	
				

}

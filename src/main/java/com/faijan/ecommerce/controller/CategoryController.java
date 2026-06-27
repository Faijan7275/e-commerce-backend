package com.faijan.ecommerce.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faijan.ecommerce.dto.request.CategoryRequest;
import com.faijan.ecommerce.dto.response.CategoryResponse;
import com.faijan.ecommerce.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService service;
	
	@PostMapping
	public CategoryResponse addCategory(@RequestBody @Valid CategoryRequest request) {
		
		return service.addCategory(request);
	}
	
	@PutMapping("/{id}")
	public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
		return service.updateCategory(id, request);
	}
	
	@GetMapping("/{id}")
	public CategoryResponse getCategory(@PathVariable Long id) {
		return service.getCategory(id);
	}
	
	@GetMapping
	public Page<CategoryResponse> getAllCategory(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size,
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "asc") String direction){
		
		return service.getAllCategory(page, size, sortBy, direction);
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteCategory(@PathVariable Long id) {
		service.deleteCategory(id);
	}

}

package com.faijan.ecommerce.service;

import org.springframework.data.domain.Page;

import com.faijan.ecommerce.dto.request.CategoryRequest;
import com.faijan.ecommerce.dto.response.CategoryResponse;

public interface CategoryService {
	
	public CategoryResponse addCategory(CategoryRequest request);
	
	public CategoryResponse updateCategory(Long id, CategoryRequest request);
	
	public CategoryResponse getCategory (Long id);
	
	public Page<CategoryResponse> getAllCategory (int page, int size, String sortBy, String direction);
	
	public void deleteCategory(Long id);

}

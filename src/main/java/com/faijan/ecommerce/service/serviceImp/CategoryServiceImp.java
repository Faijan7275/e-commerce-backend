package com.faijan.ecommerce.service.serviceImp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.faijan.ecommerce.dto.request.CategoryRequest;
import com.faijan.ecommerce.dto.response.CategoryResponse;
import com.faijan.ecommerce.dto.response.ProductResponse;
import com.faijan.ecommerce.entity.Category;
import com.faijan.ecommerce.entity.Product;
import com.faijan.ecommerce.exception.AlreadyExistException;
import com.faijan.ecommerce.exception.ResourceNotFoundException;
import com.faijan.ecommerce.repository.CategoryRepository;
import com.faijan.ecommerce.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService{
	
	private final CategoryRepository repository;

	@Override
	public CategoryResponse addCategory(CategoryRequest request) {
		
		if(repository.existsByName(request.getName())) {
			throw new AlreadyExistException("Category already exists.");
		}
		
		Category newCategory = new Category();
		
		newCategory.setName(request.getName());
		
		repository.save(newCategory);
		
		return toCategoryResponse(newCategory);
	}


	@Override
	public CategoryResponse updateCategory(Long id, CategoryRequest request) {
		
		Category existingCategory = findByCategoryId(id);
		
		if(repository.existsByName(request.getName()) && !existingCategory.getName().equals(request.getName())) {
			throw new AlreadyExistException("Category already exists.");
		}
		
		existingCategory.setName(request.getName());
		
		Category saved = repository.save(existingCategory);
		
		return toCategoryResponse(saved);
	}

	@Override
	public CategoryResponse getCategory(Long id) {

		Category existingCategory = findByCategoryId(id);
		
		return toCategoryResponse(existingCategory);
	}

	@Override
	public Page<CategoryResponse> getAllCategory(int page, int size, String sortBy, String direction) {
		
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		PageRequest pageable = PageRequest.of(page, size, sort);
		
		return repository.findAll(pageable).map(this::toCategoryResponse);
	}

	@Override
	public void deleteCategory(Long id) {		
		
		Category category = findByCategoryId(id);
		
		repository.delete(category);
	}
	
	private Category findByCategoryId(Long id) {
		
		Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no category with this id."));
		
		return category;
	}
	
	private CategoryResponse toCategoryResponse(Category category) {
		
		CategoryResponse response = CategoryResponse.builder()
				.id(category.getId())
				.name(category.getName())
				.product(category.getProducts().stream().map(this::toProductResponse).toList())
				.build();
		
		return response;
	}


	private ProductResponse toProductResponse(Product product) {
		
		ProductResponse response = ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.price(product.getPrice())
				.inventory(product.getInventory())
				.build();
		
		return response;
	}
	
}

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

import com.faijan.ecommerce.dto.request.ProductRequest;
import com.faijan.ecommerce.dto.response.ProductResponse;
import com.faijan.ecommerce.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService service;
	
	@PostMapping
	public ProductResponse addProduct(@RequestBody @Valid ProductRequest request) {
		
		return service.addProduct(request);
	}
	
	@PutMapping("/{id}")
	public ProductResponse updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
		
		return service.updateProduct(id, request);
	}
	
	@GetMapping("/{id}")
	public ProductResponse getProduct(@PathVariable Long id) {
		
		return service.getProduct(id);
	}
	
	@GetMapping
	public Page<ProductResponse> getAllProduct(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size,
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "asc") String direction){
		
		return service.getAllProduct(page, size, sortBy, direction);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable Long id) {
		
		service.deleteProduct(id);
	}
}

package com.faijan.ecommerce.service;

import org.springframework.data.domain.Page;

import com.faijan.ecommerce.dto.request.ProductRequest;
import com.faijan.ecommerce.dto.response.ProductResponse;

public interface ProductService {
	
	public ProductResponse addProduct(ProductRequest request);
	
	public ProductResponse updateProduct(Long id, ProductRequest request);
	
	public ProductResponse getProduct(Long id);
	
	public Page<ProductResponse> getAllProduct(int page, int size, String sortBy, String direction);
	
	public void deleteProduct(Long id);

}

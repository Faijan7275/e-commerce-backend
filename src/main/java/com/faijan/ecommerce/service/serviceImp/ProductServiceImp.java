package com.faijan.ecommerce.service.serviceImp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.faijan.ecommerce.dto.request.ProductRequest;
import com.faijan.ecommerce.dto.response.ProductResponse;
import com.faijan.ecommerce.entity.Category;
import com.faijan.ecommerce.entity.Product;
import com.faijan.ecommerce.exception.ResourceNotFoundException;
import com.faijan.ecommerce.repository.CategoryRepository;
import com.faijan.ecommerce.repository.ProductRepository;
import com.faijan.ecommerce.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public ProductResponse addProduct(ProductRequest request) {
		
		Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("There is no category with this id."));
		
		Product newProduct = new Product();
		
		newProduct.setName(request.getName());
		newProduct.setPrice(request.getPrice());
		newProduct.setInventory(request.getInventory());
		newProduct.setCategory(category);
		
		Product saved = productRepository.save(newProduct);
		
		return toProductResponse(saved);
	}

	@Override
	public ProductResponse updateProduct(Long id, ProductRequest request) {
		Product existingProduct = findByProductId(id);
		
		Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("There is no category with this id."));
		
		existingProduct.setName(request.getName());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setCategory(category);
		
		Product saved = productRepository.save(existingProduct);
		
		return toProductResponse(saved);
	}

	@Override
	public ProductResponse getProduct(Long id) {
		Product existingProduct = findByProductId(id);
		
		return toProductResponse(existingProduct);
	}

	@Override
	public Page<ProductResponse> getAllProduct(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		
		PageRequest pagable = PageRequest.of(page, size,sort);
		
		return productRepository.findAll(pagable).map(this::toProductResponse);
	}

	@Override
	public void deleteProduct(Long id) {
		Product existingProduct = findByProductId(id);
		
		productRepository.delete(existingProduct);
		
	}
	
	private Product findByProductId(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no product with this id."));
		
		return product;
	}
	
	private ProductResponse toProductResponse(Product product) {
		ProductResponse response = ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.price(product.getPrice())
				.inventory(product.getInventory())
				.categoryName(product.getCategory().getName())
				.build();
		
		return response;
	}

}

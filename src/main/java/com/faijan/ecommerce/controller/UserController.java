package com.faijan.ecommerce.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faijan.ecommerce.dto.request.UserRequest;
import com.faijan.ecommerce.dto.response.UserResponse;
import com.faijan.ecommerce.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService service;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public UserResponse addUser(@RequestBody @Valid UserRequest user) {
		
		UserResponse response = service.addUser(user);
		
		return response;
	}
	
	@PutMapping("/{id}")
	public UserResponse updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest user) {
		
		UserResponse response = service.updateUser(id, user);
		
		return response;		
	}
	
	@GetMapping("/{id}")
	public UserResponse getUser(@PathVariable Long id) {
		
		UserResponse response = service.getUser(id);
		
		return response;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public Page<UserResponse> getAllUser(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size,
	        @RequestParam(defaultValue = "id") String sortBy,
	        @RequestParam(defaultValue = "asc") String direction) {

	    return service.getAllUser(page, size, sortBy, direction);
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		
		service.deleteUser(id);		
	}

}

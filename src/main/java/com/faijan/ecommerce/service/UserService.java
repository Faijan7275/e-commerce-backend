package com.faijan.ecommerce.service;

import org.springframework.data.domain.Page;

import com.faijan.ecommerce.dto.request.UserRequest;
import com.faijan.ecommerce.dto.response.UserResponse;

public interface UserService {
	
	public UserResponse addUser(UserRequest user);
	
	public UserResponse updateUser(Long id, UserRequest user);
	
	public UserResponse getUser(Long id);
	
	public Page<UserResponse> getAllUser(int page, int size, String sortBy, String direction);
	
	public void deleteUser(Long id);

}

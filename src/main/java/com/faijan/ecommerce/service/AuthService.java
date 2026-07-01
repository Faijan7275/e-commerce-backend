package com.faijan.ecommerce.service;

import com.faijan.ecommerce.dto.request.LoginRequest;
import com.faijan.ecommerce.dto.request.RegisterRequest;
import com.faijan.ecommerce.dto.response.AuthResponse;

public interface AuthService {
	
	AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
	
}

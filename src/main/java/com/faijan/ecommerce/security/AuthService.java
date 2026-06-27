package com.faijan.ecommerce.security;

public interface AuthService {
	
	AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
	
}

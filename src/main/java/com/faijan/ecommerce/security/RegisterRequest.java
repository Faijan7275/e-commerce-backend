package com.faijan.ecommerce.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
	
	@NotBlank(message = "First name needed")
	private String firstName;
	
	private String lastName;
	
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email needed")
	private String email;
	
	@NotBlank(message = "Password needed")	
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
	
	@Size(min = 10, max = 10, message = "Phone number must be of size 10")
	private String phoneNumber;


}

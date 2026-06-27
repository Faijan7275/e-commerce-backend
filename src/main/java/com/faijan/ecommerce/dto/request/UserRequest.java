package com.faijan.ecommerce.dto.request;

import com.faijan.ecommerce.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
	
	@NotBlank(message = "First name needed")
	private String firstName;
	
	private String lastName;
	
	@NotNull(message = "Role needed")
	private Role role;
	
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email needed")
	private String email;
	
	@NotBlank(message = "Password needed")	
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
	
	@Size(min = 10, max = 10, message = "Phone number must be of size 10")
	private String phoneNumber;

}

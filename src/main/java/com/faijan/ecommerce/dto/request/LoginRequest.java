package com.faijan.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	
	@NotBlank(message = "Email is needed.")
	private String email;
	
	@NotBlank(message = "Password is needed.")
	private String password;

}

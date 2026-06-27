package com.faijan.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
	
	@NotBlank(message = "Name needed.")
	private String name;

}

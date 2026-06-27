package com.faijan.ecommerce.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponse {
	
	private Long id;
	
	private String name;
	
	private List<ProductResponse> product;

}

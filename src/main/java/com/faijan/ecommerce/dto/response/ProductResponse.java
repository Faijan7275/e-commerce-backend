package com.faijan.ecommerce.dto.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
	
	Long id;
	
	String name;
	
	BigDecimal price;
	
	Integer inventory;	
	
	String categoryName;

}

package com.faijan.ecommerce.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {
	
	private Long id;
	
	private String productName;
	
	private int quantity;
	
	private BigDecimal price;

}

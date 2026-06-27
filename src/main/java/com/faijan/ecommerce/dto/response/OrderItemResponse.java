package com.faijan.ecommerce.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
	
	private Long id;
	
	private String productName;
	
	private BigDecimal price;
	
	private int quantity;
	
	private BigDecimal subtotal;

}

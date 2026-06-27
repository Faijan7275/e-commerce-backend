package com.faijan.ecommerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {

	@NotNull(message = "Product id is needed.")
	private Long productId;

	@Min(value = 1, message = "Quantity must be at least 1")
	private int quantity;
}

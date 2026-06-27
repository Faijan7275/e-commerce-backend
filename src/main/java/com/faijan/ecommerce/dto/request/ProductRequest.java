package com.faijan.ecommerce.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
	
	@NotBlank(message = "Name is needed.")
	String name;
	
	@NotNull(message = "Price is needed.")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price can not be zero")
	BigDecimal price;
	
	@NotNull(message = "Inventory is needed")
	@Min(value = 0, message = "Inventory can not be zero")
	Integer inventory;	
	
	@NotNull(message = "Category Id is needed.")
	Long categoryId;

}

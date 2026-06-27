package com.faijan.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.faijan.ecommerce.entity.OrderStatus;

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
public class OrderResponse {
	
	private Long id;
	private Long userId;
	private List<OrderItemResponse> orderItems;
	private BigDecimal totalAmount;
	private OrderStatus status;
	private LocalDateTime orderDate;
	private String address;

}

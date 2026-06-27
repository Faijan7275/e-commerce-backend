package com.faijan.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faijan.ecommerce.dto.request.OrderRequest;
import com.faijan.ecommerce.dto.request.UpdateOrderStatusRequest;
import com.faijan.ecommerce.dto.response.OrderResponse;
import com.faijan.ecommerce.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService service;
	
	@PostMapping
	public OrderResponse placeOrder(@RequestParam Long userId, @RequestBody @Valid OrderRequest request) {
		return service.placeOrder(userId, request);
	}
	
	@GetMapping("/{orderId}")
	public OrderResponse getOrder(@PathVariable Long orderId, @RequestParam Long userId) {
		return service.getOrder(orderId, userId);
	}
	
	@GetMapping("/my-orders")
	public List<OrderResponse> getOrdersByUser(@RequestParam Long userId){
		return service.getOrdersByUser(userId);
	}
	
	@GetMapping
	public List<OrderResponse> getAllOrders(){
		return service.getAllOrders();
	}
	
	@PutMapping("/{orderId}")
	public OrderResponse updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest status) {
		return service.updateOrderStatus(orderId, status);
	}
	
	@DeleteMapping("/{orderId}")
	public void cancelOrder(@PathVariable Long orderId, @RequestParam Long userId) {
		service.cancelOrder(orderId, userId);
	}
}

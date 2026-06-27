package com.faijan.ecommerce.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faijan.ecommerce.dto.request.CartItemRequest;
import com.faijan.ecommerce.dto.request.UpdateQuantityRequest;
import com.faijan.ecommerce.dto.response.CartItemResponse;
import com.faijan.ecommerce.dto.response.CartResponse;
import com.faijan.ecommerce.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
	
	private final CartService service;
	
	@PostMapping("/items")
	public CartItemResponse addProductToCart(@RequestParam Long userId, @RequestBody @Valid CartItemRequest request) {
		return service.addProductToCart(userId, request);
	}
	
	@PutMapping("/items/{cartItemId}")
	public CartItemResponse updateItemQuantity(@RequestParam Long userId, @PathVariable Long cartItemId, @RequestBody @Valid UpdateQuantityRequest request) {
		return service.updateItemQuantity(userId, cartItemId, request.getQuantity());
	}
	
	@DeleteMapping("/items/{cartItemId}")
	public void removeItemFromCart(@RequestParam Long userId, @PathVariable Long cartItemId) {
		service.removeItemFromCart(userId, cartItemId);
	}
	
	@GetMapping
	public CartResponse getCart(@RequestParam Long userId) {
		return service.getCart(userId);
	}
	
	@DeleteMapping
	public void clearCart(@RequestParam Long userId) {
		service.clearCart(userId);
	}

}

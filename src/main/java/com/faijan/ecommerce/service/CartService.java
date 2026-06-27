package com.faijan.ecommerce.service;

import com.faijan.ecommerce.dto.request.CartItemRequest;
import com.faijan.ecommerce.dto.response.CartItemResponse;
import com.faijan.ecommerce.dto.response.CartResponse;

public interface CartService {
	
	public CartItemResponse addProductToCart(Long userId, CartItemRequest request);
	
	public CartItemResponse updateItemQuantity(Long userid, Long itemId, int quantiy);
	
	public void removeItemFromCart(Long userid, Long itemId);
	
	public CartResponse getCart(Long id);
	
	public void clearCart(Long userId);

}

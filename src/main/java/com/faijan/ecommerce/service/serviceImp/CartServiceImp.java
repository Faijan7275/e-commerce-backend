package com.faijan.ecommerce.service.serviceImp;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.faijan.ecommerce.dto.request.CartItemRequest;
import com.faijan.ecommerce.dto.response.CartItemResponse;
import com.faijan.ecommerce.dto.response.CartResponse;
import com.faijan.ecommerce.entity.Cart;
import com.faijan.ecommerce.entity.CartItem;
import com.faijan.ecommerce.entity.Product;
import com.faijan.ecommerce.entity.User;
import com.faijan.ecommerce.exception.ResourceNotFoundException;
import com.faijan.ecommerce.repository.CartItemRepository;
import com.faijan.ecommerce.repository.CartRepository;
import com.faijan.ecommerce.repository.ProductRepository;
import com.faijan.ecommerce.repository.UserRepository;
import com.faijan.ecommerce.service.CartService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService{
	
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	@Override
	public CartItemResponse addProductToCart(Long userId, CartItemRequest request) {
		
		Cart cart = cartRepository.findByUserId(userId).orElseGet( () -> {
				User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with this id not exist."));
				Cart newCart = new Cart();
				newCart.setUser(user);
				return cartRepository.save(newCart);
		});
		
		Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("There is product with this id"));
		
		
		CartItem existingCartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId()).orElseGet(() ->{
			CartItem newCartItem = new CartItem();
			newCartItem.setCart(cart);
			newCartItem.setProduct(product);
			newCartItem.setQuantity(0);
			return newCartItem;
		});
		existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
		CartItem saved = cartItemRepository.save(existingCartItem);
		return toCartItemResponse(saved);
	}

	@Override
	public CartItemResponse updateItemQuantity(Long userId, Long cartItemId, int quantity) {
		
		CartItem existingCartItem = cartItemRepository.findByIdAndCart_User_Id(cartItemId, userId).orElseThrow(() -> new ResourceNotFoundException("There is no cart item with this id"));
		existingCartItem.setQuantity(quantity);
		CartItem saved = cartItemRepository.save(existingCartItem);
		
		return toCartItemResponse(saved);
	}

	@Override
	public void removeItemFromCart(Long userId, Long cartItemId) {
		
		CartItem existingCartItem = cartItemRepository.findByIdAndCart_User_Id(cartItemId, userId).orElseThrow(() -> new ResourceNotFoundException("There is no cart item with this id"));
		cartItemRepository.delete(existingCartItem);
		
	}

	@Override
	public CartResponse getCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("There is no cart with this id."));
		
		CartResponse response = CartResponse.builder()
				.id(cart.getId())
				.userId(cart.getUser().getId())
				.cartItems(cart.getCartItems().stream().map(this::toCartItemResponse).toList())
				.build();
						
		return response;
	}

	@Override
	@Transactional
	public void clearCart(Long userId) {
		Cart existingCart = cartRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("There is no cart with this id."));
		existingCart.getCartItems().clear();
	    cartRepository.save(existingCart);		
	}
	
	private CartItemResponse toCartItemResponse(CartItem cartItem) {
		CartItemResponse response = CartItemResponse.builder()
				.id(cartItem.getId())
				.productName(cartItem.getProduct().getName())
				.quantity(cartItem.getQuantity())
				.price(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
				.build();
		
		return response;
	}
	
	

}

package com.faijan.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faijan.ecommerce.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	   Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
	   Optional<CartItem> findByIdAndCart_User_Id(Long id, Long userId);
	

}

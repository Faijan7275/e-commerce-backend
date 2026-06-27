package com.faijan.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faijan.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findByUserId(Long userId);
	
	Optional<Order> findByIdAndUser_Id(Long orderId, Long userId);

}

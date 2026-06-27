package com.faijan.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faijan.ecommerce.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}

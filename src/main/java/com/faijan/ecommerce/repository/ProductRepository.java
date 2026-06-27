package com.faijan.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faijan.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}

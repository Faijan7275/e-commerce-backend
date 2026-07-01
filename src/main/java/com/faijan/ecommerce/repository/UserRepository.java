package com.faijan.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.faijan.ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findUserByEmail(String Email);

}

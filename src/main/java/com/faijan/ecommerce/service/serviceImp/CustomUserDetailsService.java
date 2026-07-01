package com.faijan.ecommerce.service.serviceImp;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.faijan.ecommerce.entity.CustomUserDetails;
import com.faijan.ecommerce.entity.User;
import com.faijan.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("There is no user with this email."));
		return new CustomUserDetails(user);
	}

}

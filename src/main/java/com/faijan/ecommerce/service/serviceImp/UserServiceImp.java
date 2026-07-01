package com.faijan.ecommerce.service.serviceImp;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.faijan.ecommerce.dto.request.UserRequest;
import com.faijan.ecommerce.dto.response.UserResponse;
import com.faijan.ecommerce.entity.User;
import com.faijan.ecommerce.exception.AlreadyExistException;
import com.faijan.ecommerce.exception.ResourceNotFoundException;
import com.faijan.ecommerce.repository.UserRepository;
import com.faijan.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
	
	private final UserRepository repository;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserResponse addUser(UserRequest user) {
		
		Optional<User> userWithSameEmail = repository.findUserByEmail(user.getEmail());
		
		if(userWithSameEmail.isPresent()) {
			throw new AlreadyExistException("Given email already exists.");
		}
		
		User newUser = new User();
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setPhoneNumber(user.getPhoneNumber());
		newUser.setRole(user.getRole());
		newUser.setCreatedAt(LocalDateTime.now());
		newUser.setUpdatedAt(LocalDateTime.now());
		
		User savedUser = repository.save(newUser);
		
		UserResponse response = toResponseDto(savedUser);
		
		return response;
	}

	@Override
	public UserResponse updateUser(Long id, UserRequest user) {
		
		User existingUser = findByUserId(id);

		Optional<User> userWithSameEmail =
		        repository.findUserByEmail(user.getEmail());

		if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(existingUser.getId())) {
		    throw new AlreadyExistException("Given email already exists.");
		}
		
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		existingUser.setPhoneNumber(user.getPhoneNumber());
		existingUser.setRole(user.getRole());
		existingUser.setUpdatedAt(LocalDateTime.now());
		
		User savedUser = repository.save(existingUser);
		
		UserResponse response = toResponseDto(savedUser);
		
		return response;		
	}

	@Override
	public UserResponse getUser(Long id) {
		
		User existingUser = findByUserId(id);
		
		UserResponse response = toResponseDto(existingUser);
		
		return response;
	}

	@Override
		public Page<UserResponse> getAllUser(int page, int size, String sortBy, String direction) {

		    Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		    PageRequest pageable = PageRequest.of(page, size, sort);

		    return repository.findAll(pageable).map(this::toResponseDto);
		}

	@Override
	public void deleteUser(Long id) {
		
		User existingUser = findByUserId(id);
		
		repository.delete(existingUser);
	}
	
	private UserResponse toResponseDto(User savedUser) {
		
		UserResponse response = UserResponse.builder()
				.id(savedUser.getId())
				.firstName(savedUser.getFirstName())
				.lastName(savedUser.getLastName())
				.email(savedUser.getEmail())
				.phoneNumber(savedUser.getPhoneNumber())
				.role(savedUser.getRole())
				.build();
		
		return response;
		
	}
	
	private User findByUserId(Long id) {
		User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There is no user with this id."));
		return user;
	}
	
}

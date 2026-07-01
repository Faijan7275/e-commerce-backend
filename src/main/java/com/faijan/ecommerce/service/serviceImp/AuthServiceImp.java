package com.faijan.ecommerce.service.serviceImp;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.faijan.ecommerce.dto.request.LoginRequest;
import com.faijan.ecommerce.dto.request.RegisterRequest;
import com.faijan.ecommerce.dto.response.AuthResponse;
import com.faijan.ecommerce.entity.Role;
import com.faijan.ecommerce.entity.User;
import com.faijan.ecommerce.exception.AlreadyExistException;
import com.faijan.ecommerce.repository.UserRepository;
import com.faijan.ecommerce.security.JwtUtil;
import com.faijan.ecommerce.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
            throw new AlreadyExistException("A user with this email already exists.");
        }

        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setRole(Role.CUSTOMER);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        User saved = userRepository.save(newUser);

        String token = jwtUtil.generateToken(saved.getEmail());

        return AuthResponse.builder()
                .token(token)
                .userId(saved.getId())
                .email(saved.getEmail())
                .role(saved.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("User should exist after successful authentication."));

        String token = jwtUtil.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
package com.blogapi.blogplatform.service.impl;

import com.blogapi.blogplatform.config.SecurityConfig;
import com.blogapi.blogplatform.dto.AuthResponse;
import com.blogapi.blogplatform.dto.LoginRequest;
import com.blogapi.blogplatform.dto.RegisterRequest;
import com.blogapi.blogplatform.model.User;
import com.blogapi.blogplatform.repository.UserRepository;
import com.blogapi.blogplatform.security.JwtTokenProvider;
import com.blogapi.blogplatform.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String register(RegisterRequest registerRequest) {
        // Check if a user with the same username already exists
        if (userRepository.findByUsername(registerRequest.username()).isPresent()) {
            throw new RuntimeException("Error: Username is already taken!");
        }
        // Check if a user with the same email already exists
        if (userRepository.findByEmail(registerRequest.email()).isPresent()) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Create the new user
        User newUser = new User();
        newUser.setUsername(registerRequest.username());
        newUser.setEmail(registerRequest.email());
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));

        userRepository.save(newUser);

        return "User Registered Successfully";
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        // If authentication is successful, set it in the context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate the JWT token
        String token = jwtTokenProvider.generateToken(authentication);

        return new AuthResponse(token);
    }
}

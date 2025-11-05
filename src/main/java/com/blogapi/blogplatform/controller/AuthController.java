package com.blogapi.blogplatform.controller;

import com.blogapi.blogplatform.dto.AuthResponse;
import com.blogapi.blogplatform.dto.LoginRequest;
import com.blogapi.blogplatform.dto.RegisterRequest;
import com.blogapi.blogplatform.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // @Valid -> Triggers the validation we put in the DTO
        String response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

}

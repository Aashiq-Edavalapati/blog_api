package com.blogapi.blogplatform.service;

import com.blogapi.blogplatform.dto.AuthResponse;
import com.blogapi.blogplatform.dto.LoginRequest;
import com.blogapi.blogplatform.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);

}

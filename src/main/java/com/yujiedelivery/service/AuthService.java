package com.yujiedelivery.service;

import com.yujiedelivery.dto.JwtResponse;
import com.yujiedelivery.dto.LoginRequest;
import com.yujiedelivery.dto.SignupRequest;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
    JwtResponse signup(SignupRequest signupRequest);
} 
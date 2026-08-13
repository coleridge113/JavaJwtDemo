package com.luna.jwt_demo.auth.service;

import org.springframework.stereotype.Service;
import com.luna.jwt_demo.auth.model.dto.RegisterRequest;
import com.luna.jwt_demo.auth.repository.UserInfoRepository;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserInfoRepository repository;

    public AuthService(
        JwtService jwtService,
        UserInfoRepository repository
    ) {
        this.jwtService = jwtService;
        this.repository = repository;
    }

    public String authenticateUser(String username, String password) {
        String token = jwtService.generateToken(username, "user");
        return token;
    }

    // public boolean registerUser(RegisterRequest request) {
    //
    // }
}

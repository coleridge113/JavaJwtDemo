package com.luna.jwt_demo.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private JwtService jwtService;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String authenticateUser(String username, String password) {
        String token = jwtService.generateToken(username, "user");
        return token;
    }
}

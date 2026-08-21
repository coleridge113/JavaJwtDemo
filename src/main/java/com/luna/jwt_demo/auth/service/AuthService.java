package com.luna.jwt_demo.auth.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.luna.jwt_demo.auth.model.entity.UserInfo;
import com.luna.jwt_demo.auth.repository.UserInfoRepository;
import com.luna.jwt_demo.common.security.JwtService;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserInfoRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
        JwtService jwtService,
        UserInfoRepository repository,
        PasswordEncoder passwordEncoder
    ) {
        this.jwtService = jwtService;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticateUser(String username, String password) {
        UserInfo user = repository.findByUsername(username)
            .orElseThrow(() -> new BadCredentialsException("Invalid credentials!"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials!");
        }


        return jwtService.generateToken(user.getId(), username, "user");
    }

    public void registerUser(String username, String password) {
        if (repository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists!");
        }

        String encodedPassword = passwordEncoder.encode(password);
        UserInfo user = new UserInfo(
            username, 
            encodedPassword, 
            "USER"
        );

        repository.save(user);
    }
}

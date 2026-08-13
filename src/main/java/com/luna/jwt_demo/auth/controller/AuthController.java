package com.luna.jwt_demo.auth.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luna.jwt_demo.auth.model.dto.LoginRequest;
import com.luna.jwt_demo.auth.model.dto.RegisterRequest;
import com.luna.jwt_demo.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1")
class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> authenticateUser(@RequestBody LoginRequest request) {
        String token = authService.authenticateUser(
            request.username(),
            request.password()
        );

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        // authService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Successfully added user");
    }

}

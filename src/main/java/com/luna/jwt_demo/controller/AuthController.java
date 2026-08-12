package com.luna.jwt_demo.controller;

import com.luna.jwt_demo.data.dto.LoginRequest;
import com.luna.jwt_demo.service.AuthService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
class AuthController {

    private AuthService authService;

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

}

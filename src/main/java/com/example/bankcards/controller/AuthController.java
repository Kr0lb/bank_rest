package com.example.bankcards.controller;

import com.example.bankcards.dto.request.AuthRequest;
import com.example.bankcards.dto.response.AuthResponse;
import com.example.bankcards.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }
}
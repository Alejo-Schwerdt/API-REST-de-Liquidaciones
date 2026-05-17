package com.transportista.liquidaciones.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.transportista.liquidaciones.dto.request.LoginRequest;
import com.transportista.liquidaciones.dto.request.RegisterRequest;
import com.transportista.liquidaciones.dto.response.AuthResponse;
import com.transportista.liquidaciones.service.AuthService; 

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
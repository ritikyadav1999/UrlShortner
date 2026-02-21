package org.example.urlshortner.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.urlshortner.dto.LoginRequest;
import org.example.urlshortner.dto.LoginResponse;
import org.example.urlshortner.dto.RegisterRequest;
import org.example.urlshortner.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }



}

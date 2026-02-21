package org.example.urlshortner.service;

import lombok.RequiredArgsConstructor;
import org.example.urlshortner.dto.LoginRequest;
import org.example.urlshortner.dto.LoginResponse;
import org.example.urlshortner.dto.RegisterRequest;
import org.example.urlshortner.entity.User;
import org.example.urlshortner.jwt.JwtService;
import org.example.urlshortner.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request){
        if (userRepo.findByEmail(request.email()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User user   = new User();
        user.setEmail(request.email());
        user.setPassword(this.passwordEncoder.encode(request.password()));
        user.setRole(User.Role.USER);
        user.setName(request.name());

        userRepo.save(user);
        return "Successfully Created";
    }

    public LoginResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        return new LoginResponse(jwtService.generateToken(request.email()));
    }

}

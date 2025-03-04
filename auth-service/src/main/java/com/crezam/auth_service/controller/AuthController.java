package com.crezam.auth_service.controller;

import com.crezam.auth_service.dto.JwtRequest;
import com.crezam.auth_service.dto.JwtResponse;
import com.crezam.auth_service.entity.User;
import com.crezam.auth_service.security.JwtUtil;
import com.crezam.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User userRequest) {
        System.out.println("Register api hit!");
        User user = authService.createUser(userRequest);
        return ResponseEntity.ok(user.getId()); // Return UUID
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        User user = authService.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token;
        if(authentication.isAuthenticated()){
            token = jwtUtil.generateToken(user.getEmail());
        }
        else{
            throw new RuntimeException("invalid access");
        }
        return ResponseEntity.ok(new JwtResponse(token));
    }


}

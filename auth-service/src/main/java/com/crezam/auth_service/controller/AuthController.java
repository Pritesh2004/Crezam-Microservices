package com.crezam.auth_service.controller;

import com.crezam.auth_service.model.dto.JwtRequest;
import com.crezam.auth_service.model.dto.JwtResponse;
import com.crezam.auth_service.model.User;
import com.crezam.auth_service.security.JwtUtil;
import com.crezam.auth_service.service.AuthService;
import com.crezam.auth_service.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, OtpService otpService) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.otpService = otpService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User userRequest) {
        System.out.println("Register api hit!");
        return authService.createUser(userRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        User user = authService.findByEmail(request.getEmail());
        if(user==null){
            throw new RuntimeException("User not found while login : "+request.getEmail());
        }

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

    @PutMapping("/updatePassword")
    public String updatePassword(@RequestParam String email,
                                 @RequestParam String newPassword) {
        return authService.updatePassword(email, newPassword);
    }


    // Send OTP
    @PostMapping("/sendOtp")
    public String sendOtp(@RequestParam String email) {
        otpService.sendOtp(email);
        return "OTP sent to " + email;
    }

    // Verify OTP
    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return otpService.validateOtp(email, otp) ? "OTP verified!" : "Invalid OTP";
    }


}

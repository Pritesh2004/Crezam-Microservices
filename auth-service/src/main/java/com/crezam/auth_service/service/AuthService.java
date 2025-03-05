package com.crezam.auth_service.service;

import com.crezam.auth_service.client.UserServiceClient;
import com.crezam.auth_service.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserServiceClient userServiceClient;

    public AuthService(BCryptPasswordEncoder passwordEncoder, UserServiceClient userServiceClient) {
        this.passwordEncoder = passwordEncoder;
        this.userServiceClient = userServiceClient;
    }

    public User createUser(User userRequest) {
        return userServiceClient.createUser(userRequest);
    }

    public User findByEmail(String email) {
        return userServiceClient.getUserByEmail(email);
    }

    public String updatePassword(String email, String newPassword) {
        return userServiceClient.updatePassword(email, newPassword);
    }
}

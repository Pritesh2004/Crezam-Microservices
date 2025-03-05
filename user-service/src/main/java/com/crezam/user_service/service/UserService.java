package com.crezam.user_service.service;

import com.crezam.user_service.entity.User;
import com.crezam.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User already exists: " + user.getEmail());
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);

        return newUser;
    }

    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public boolean updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user !=null) {
            user.setPassword(passwordEncoder.encode(newPassword)); // Hash password before saving
            userRepository.save(user);
            return true;
        }
        return false;
    }
}

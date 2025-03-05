package com.crezam.user_service.controller;

import com.crezam.user_service.entity.User;
import com.crezam.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{message}")
    public ResponseEntity<String> secureApi(@PathVariable String message){
        System.out.println("Secure api = "+message);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestParam String email,
                                                 @RequestParam String newPassword) {
        boolean updated = userService.updatePassword(email, newPassword);
        return updated ? ResponseEntity.ok("Password updated successfully")
                : ResponseEntity.badRequest().body("User not found");
    }
}

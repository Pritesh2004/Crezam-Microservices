package com.crezam.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserProfileController {

    @GetMapping("/{message}")
    public ResponseEntity<String> secureApi(@PathVariable String message){
        System.out.println("Secure api = "+message);
        return ResponseEntity.ok(message);
    }
}

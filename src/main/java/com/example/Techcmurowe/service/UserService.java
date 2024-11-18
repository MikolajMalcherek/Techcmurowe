package com.example.Techcmurowe.service;

import com.example.Techcmurowe.model.User;
import com.example.Techcmurowe.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> CreateUser(String username) {
        System.out.println("User searching: " + username);
        if (!userRepository.findByUsername(username).isPresent()) {
            User user = new User();
            user.setUsername(username);
            userRepository.save(user);
            System.out.println("User created");
            return ResponseEntity.status(HttpStatus.CREATED).body("Created user successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
    }

}

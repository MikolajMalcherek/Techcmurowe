package com.example.Techcmurowe.service;

import com.example.Techcmurowe.model.User;
import com.example.Techcmurowe.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> CreateUser(String username) {
        System.out.println("User searching: " + username);
        if (!userRepository.findByUsername(username).isPresent()) {
            User user = new User();
            user.setUsername(username);
            userRepository.save(user);
            System.out.println("User created");

            Map<String, String> map = new HashMap<String,String>();
            map.put("message", "User created");

            return new ResponseEntity<Object>(map, HttpStatus.CREATED);

            //return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Created user successfully - backend response"));
        }
        else {
            Map<String, String> map = new HashMap<String, String>();
            map.put("message", "User already exists");

            return new ResponseEntity<Object>(map, HttpStatus.CONFLICT);
            //return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists - backend response");
        }
    }

}

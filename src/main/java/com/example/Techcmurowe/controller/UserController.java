package com.example.Techcmurowe.controller;

import com.example.Techcmurowe.model.User;
import com.example.Techcmurowe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}

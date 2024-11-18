package com.example.Techcmurowe.controller;

import com.example.Techcmurowe.DTO.UserResponseDTO;
import com.example.Techcmurowe.model.User;
import com.example.Techcmurowe.repository.UserRepository;
import com.example.Techcmurowe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/check")
    public ResponseEntity<Void> getUser(@RequestParam String username) {
        boolean userExists = userRepository.findByUsername(username).isPresent();

        if (userExists) {
            return ResponseEntity.ok().build(); // Użytkownik istnieje
        } else {
            return ResponseEntity.notFound().build(); // Użytkownik nie istnieje
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserResponseDTO user) {

        return userService.CreateUser(user.getUsername());
    }
}

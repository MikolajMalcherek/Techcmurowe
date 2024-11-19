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
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/getuser")
    public ResponseEntity<User> getUser(@RequestParam String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/check")
    public ResponseEntity<Void> checkUser(@RequestParam String username) {
        boolean userExists = userRepository.findByUsername(username).isPresent();

        if (userExists) {
            return ResponseEntity.ok().build(); // Użytkownik istnieje
        } else {
            return ResponseEntity.notFound().build(); // Użytkownik nie istnieje
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserResponseDTO user) {

        return userService.CreateUser(user.getUsername());
    }
}

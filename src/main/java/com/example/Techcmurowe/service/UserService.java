package com.example.Techcmurowe.service;

import com.example.Techcmurowe.model.User;
import com.example.Techcmurowe.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User CreateUser(User user) {
        User newUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (newUser != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        else userRepository.save(user);
        return newUser;
    }

}

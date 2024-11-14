package com.example.Techcmurowe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    private final CognitoService cognitoService;

    @Autowired
    public AuthController(CognitoService cognitoService) {
        this.cognitoService = cognitoService;
    }

    @GetMapping("/callback")
    public Mono<String> callback(@RequestParam String code) {
        // Zamiast tego kodu normalnie wysyłasz kod do Cognito, aby uzyskać token
        return cognitoService.getToken(code);
    }
}

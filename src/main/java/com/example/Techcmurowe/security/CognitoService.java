package com.example.Techcmurowe.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CognitoService {

    @Value("${spring.security.oauth2.client.provider.cognito.token-uri}")
    private String COGNITO_TOKEN_URL;

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String COGNITO_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.cognito.client-secret}")
    private String COGNITO_CLIENT_SECRET;

    @Value("${spring.security.oauth2.client.registration.cognito.redirect-uri}")
    private String COGNITO_REDIRECT_URI;

    private final WebClient webClient;


    public CognitoService(WebClient.Builder webClient,
                          @Value("${spring.security.oauth2.client.provider.cognito.token-uri}") String cognitoTokenUrl) {
        this.webClient = webClient.baseUrl(cognitoTokenUrl).build();
    }

    public Mono<String> getToken(String code) {

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", COGNITO_CLIENT_ID);
        body.add("client_secret", COGNITO_CLIENT_SECRET);
        body.add("code", code);
        body.add("redirect_uri", COGNITO_REDIRECT_URI);


        return webClient.post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class);
    }

}

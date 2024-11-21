//package com.example.Techcmurowe.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//
//@Configuration
//public class JwtDecoderConfig {
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        String jwkSetUri = "https://cognito-idp.us-east-1.amazonaws.com/us-east-1_aMufiEQBO/.well-known/jwks.json";
//        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
//    }
//}

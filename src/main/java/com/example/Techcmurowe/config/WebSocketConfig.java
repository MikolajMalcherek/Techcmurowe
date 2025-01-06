package com.example.Techcmurowe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableMethodSecurity
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setPreservePublishOrder(true);
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/websocket").setAllowedOriginPatterns("http://localhost:4200");
//        registry.addEndpoint("/websocket").setAllowedOrigins("http://localhost:4200").withSockJS();
//        registry.addEndpoint("/websocket").setAllowedOriginPatterns("https://localhost:4200");
//        registry.addEndpoint("/websocket").setAllowedOrigins("https://localhost:4200").withSockJS();
//        registry.addEndpoint("/websocket").setAllowedOriginPatterns("https://52.3.73.243:443");
        registry.addEndpoint("/websocket").setAllowedOrigins("https://52.3.73.243:443",
                "https://52.3.73.243", "https://50.16.52.223",
                "https://50.16.52.223:443", "https://localhost:443",
                "https://localhost", "https://message-app-1932806518.us-east-1.elb.amazonaws.com:443",
                "https://message-app-1932806518.us-east-1.elb.amazonaws.com").withSockJS();
//        registry.addEndpoint("/websocket").setAllowedOriginPatterns("https://52.3.73.243:80");
//        registry.addEndpoint("/websocket").setAllowedOrigins("https://52.3.73.243:80").withSockJS();
    }


}

package com.example.microservices.api_gateway;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtDecoderConfig {

    @Value("${jwt.secret}") // Inject the secret key from Config Server
    private String jwtSecret;

    @Bean
    public NimbusReactiveJwtDecoder reactiveJwtDecoder() {
        // Configure the ReactiveJwtDecoder with the shared secret
        SecretKey key = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA512");

        return NimbusReactiveJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS512).build();
    }
}
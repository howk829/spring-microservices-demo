package com.example.microservices.api_gateway;


import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class JwtAuthFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    private final CustomGatewayProperties gatewayProperties; // Inject GatewayProperties

    public JwtAuthFilter(JwtUtil jwtUtil, CustomGatewayProperties gatewayProperties) {
        this.jwtUtil = jwtUtil;
        this.gatewayProperties = gatewayProperties;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        log.info("Incoming request path: {}", request.getPath());
        String path = request.getPath().toString();

        String serviceName = getServiceNameFromPath(path);

        // Bypass JWT validation for public endpoints
        if (isPublicEndpoint(serviceName, path)) {
            log.info("Public endpoint accessed: {}", path);
            return chain.filter(exchange);
        }

        log.info("Private endpoint accessed: {}", path);

        // Validate JWT token
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return respondWithUnauthorized(exchange.getResponse());
        }

        String token = authHeader.substring(7); // Extract the token
        try {
            // Validate the token and extract claims
            Claims claims = jwtUtil.validateToken(token);

            // Add user details to headers for forwarding to downstream services
            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-Authenticated-User", claims.getSubject()) // Add username
                    // .header("X-User-Roles", String.join(",", (List<String>) claims.get("roles", List.class))) // Add roles if available
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        } catch (Exception e) {
            return respondWithUnauthorized(exchange.getResponse());
        }
    }

    /**
     * Extracts the service name from the request path.
     * For example, "/user-service/api/v1/register" -> "user-service"
     */
    private String getServiceNameFromPath(String path) {
        // Extract the first segment of the path (e.g., "/user-service/**" -> "user-service")
        if (path.startsWith("/")) {
            path = path.substring(1); // Remove leading slash
        }
        return path.split("/")[0]; // Extract the first segment
    }

    /**
     * Determines if the endpoint is public based on service name and endpoint path.
     * Public endpoints include:
     * - Login and register under user-service (or other specified services)
     */
    private boolean isPublicEndpoint(String serviceName, String path) {
        List<String> publicServices = gatewayProperties.getPublicServices();

        // Check if the service is in the public services list
        if (publicServices.contains(serviceName)) {
            // Allow public access to login and register endpoints
            return path.contains("/login") || path.contains("/register");
        }
        return false;
    }

    private Mono<Void> respondWithUnauthorized(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}

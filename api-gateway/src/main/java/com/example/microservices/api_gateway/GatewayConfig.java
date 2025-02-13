package com.example.microservices.api_gateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
public class GatewayConfig {
    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            System.out.println("Global Filter executed for request path: " + exchange.getRequest().getPath());
            return chain.filter(exchange);
        };
    }
}

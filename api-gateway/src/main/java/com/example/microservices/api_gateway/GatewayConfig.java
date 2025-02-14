package com.example.microservices.api_gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

@Configuration
@Slf4j
public class GatewayConfig {
    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> {
            logRequest(exchange);
            return chain.filter(exchange);
        };
    }


    private void logRequest(ServerWebExchange exchange) {
        log.info("Request: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI());
    }

    private void logResponse(ServerWebExchange exchange) {
        log.info("Response: {}", exchange.getResponse().getStatusCode());
    }
}

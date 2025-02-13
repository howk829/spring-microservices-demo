package com.example.microservices.api_gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "custom.gateway")
public class CustomGatewayProperties {

    private List<String> publicServices;

    public List<String> getPublicServices() {
        return publicServices;
    }

    public void setPublicServices(List<String> publicServices) {
        this.publicServices = publicServices;
    }
}

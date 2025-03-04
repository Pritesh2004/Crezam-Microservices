package com.crezam.api_gateway.config;

import com.crezam.api_gateway.security.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthenticationFilter jwtAuthFilter) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .uri("lb://AUTH-SERVICE"))
                .route("user-service", r -> r.path("/user/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new AuthenticationFilter.Config()))) // Apply JWT filter to user-service
                        .uri("lb://USER-SERVICE"))
                .build();
    }
}

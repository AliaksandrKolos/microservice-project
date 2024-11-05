package com.kolos.gatewayservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class GatewayConfig {

//    @Bean
//    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("resource-service", p -> p
//                        .path("/resources/**")
//                        .uri("lb://resource-service"))
//                .route("song-service", p -> p
//                        .path("/songs/**")
//                        .uri("lb://song-service"))
//                .build();
//    }
}

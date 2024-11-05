package com.kolos.gatewayservice;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/token",
            "/resources/communication-health-check",
            "/resources/all_songs",
            "/resources/song/\\d+",
            "/resources/\\d+",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().matches(uri));
}



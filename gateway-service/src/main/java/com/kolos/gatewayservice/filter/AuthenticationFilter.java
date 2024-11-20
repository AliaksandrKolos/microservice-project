package com.kolos.gatewayservice.filter;

import com.kolos.gatewayservice.RouteValidator;
import com.kolos.gatewayservice.exception.SecurityException;
import com.kolos.gatewayservice.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtUtil jwtUtil;
    private final RouteValidator validator;

    public AuthenticationFilter(JwtUtil jwtUtil, RouteValidator validator) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.validator = validator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();


            if (validator.isSecured.test(request)) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new SecurityException("Authorization header not present");
                }

                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    jwtUtil.validateToken(authHeader);

                    String role = jwtUtil.extractRole(authHeader);
                    ServerHttpRequest updatedRequest = request.mutate()
                            .header("X-User-Role", role)
                            .build();

                    exchange = exchange.mutate().request(updatedRequest).build();

                } catch (Exception e) {
                    log.info("Check token failed");
                    throw new SecurityException("Check token failed");
                }
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

}

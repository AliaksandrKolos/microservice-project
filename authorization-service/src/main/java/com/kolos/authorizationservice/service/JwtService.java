package com.kolos.authorizationservice.service;

public interface JwtService {

    void validateToken(String token);

    String generateToken(String email, String role);


}

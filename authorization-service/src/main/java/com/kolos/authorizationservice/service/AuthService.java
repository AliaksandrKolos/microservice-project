package com.kolos.authorizationservice.service;

import com.kolos.authorizationservice.service.dto.AuthRequest;

public interface AuthService {

    String saveUser(AuthRequest user);

    String generateJwtToken(String email, String role);

    void validateJwtToken(String token);

}

package com.kolos.authorizationservice.service.impl;

import com.kolos.authorizationservice.data.entity.User;
import com.kolos.authorizationservice.data.repository.UserCredentialRepository;
import com.kolos.authorizationservice.service.AuthService;
import com.kolos.authorizationservice.service.JwtService;
import com.kolos.authorizationservice.service.dto.AuthRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String generateJwtToken(String email, String role) {
        return jwtService.generateToken(email, role);
    }

    @Override
    public void validateJwtToken(String token) {
        jwtService.validateToken(token);
    }

    @Override
    @Transactional
    public String saveUser(AuthRequest authRequest) {
        log.info("Saving user {}", authRequest);
        User createUser = new User();
        createUser.setEmail(authRequest.getEmail());
        createUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        createUser.setRole(User.Role.USER);
        userCredentialRepository.save(createUser);
        return "User Saved";
    }

}

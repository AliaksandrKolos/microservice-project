package com.kolos.authorizationservice.service.impl;

import com.kolos.authorizationservice.data.entity.User;
import com.kolos.authorizationservice.data.entity.UserPrincipal;
import com.kolos.authorizationservice.data.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userCredentialRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return new UserPrincipal(user);
    }

}

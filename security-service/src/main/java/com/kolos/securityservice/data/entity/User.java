package com.kolos.securityservice.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;


    public enum Role implements GrantedAuthority {
        USER, MANAGER, ADMIN;

        @Override
        public String getAuthority() {
            return name();
        }
    }
}

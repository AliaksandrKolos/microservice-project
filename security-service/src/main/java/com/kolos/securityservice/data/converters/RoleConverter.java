package com.kolos.securityservice.data.converters;

import com.kolos.securityservice.data.entity.User;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<User.Role, Integer> {


    @Override
    public Integer convertToDatabaseColumn(User.Role role) {
        if (role == null) {
            return null;
        }

        return switch (role) {
            case USER -> 1;
            case MANAGER -> 2;
            case ADMIN -> 3;
        };
    }

    @Override
    public User.Role convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return switch (integer) {
            case 1 -> User.Role.USER ;
            case 2 -> User.Role.MANAGER;
            case 3 -> User.Role.ADMIN;
            default -> throw new IllegalArgumentException("Invalid role id " + integer);
        };
    }
}

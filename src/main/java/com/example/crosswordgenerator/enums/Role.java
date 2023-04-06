package com.example.crosswordgenerator.enums;

import org.springframework.security.core.GrantedAuthority;


/**
 * Роль пользователя в системе.
 * */
public enum Role implements GrantedAuthority {
    /**
     * Роль пользователя.
     * */
    ROLE_USER,
    /**
     * Роль администратора.
     * */
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

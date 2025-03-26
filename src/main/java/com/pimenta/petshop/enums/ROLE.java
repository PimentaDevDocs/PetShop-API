package com.pimenta.petshop.enums;

import lombok.Getter;

@Getter
public enum ROLE {
    CLIENTE("ROLE_CLIENTE"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    ROLE(String role) {
        this.role = role;
    }

    public static boolean isValid(String perfil) {
        try {
            ROLE.valueOf(perfil.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
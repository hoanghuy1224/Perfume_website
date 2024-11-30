package com.gmail.HoangHuy.ecommerce.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER,


    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
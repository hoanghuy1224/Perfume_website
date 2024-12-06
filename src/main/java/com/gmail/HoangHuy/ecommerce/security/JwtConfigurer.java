package com.gmail.HoangHuy.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigurer {
    @Autowired
    private  JwtFilter jwtFilter;

//    public JwtConfigurer(JwtFilter jwtFilter) {
//        this.jwtFilter = jwtFilter;
//    }

    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

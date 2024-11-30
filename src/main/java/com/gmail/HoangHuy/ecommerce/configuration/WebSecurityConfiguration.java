package com.gmail.HoangHuy.ecommerce.configuration;

import com.gmail.HoangHuy.ecommerce.security.JwtConfigurer;
import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration   {

    private final JwtConfigurer jwtConfigurer;

    @Autowired
    public WebSecurityConfiguration(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Áp dụng cấu hình bảo mật với các đường dẫn và quyền truy cập
        http
                .csrf(csrf -> csrf.disable())  // Vô hiệu hóa CSRF cho API RESTful
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/rest",
                                "/api/v1/rest/product/*",
                                "/api/v1/rest/admin/*",
                                "/api/v1/rest/admin/user/*",
                                "/api/v1/rest/registration",
                                "/api/v1/rest/forgot",
                                "/api/v1/rest/reset/**",
                                "/api/v1/rest/activate/*",
                                "/api/v1/rest/menu/**",
                                "/api/v1/rest/cart",
                                "/api/v1/rest/cart/*",
                                "/api/v1/rest/order",
                                "/api/v1/rest/order/*",
                                "/api/v1/rest/user/*",
                                "/img/**",
                                "/static/**",
                                "/activate/*",
                                "/menu/**")
                        .permitAll()  // Các đường dẫn này được phép truy cập không cần xác thực
                        .requestMatchers("/api/v1/rest/login").permitAll()  // Đường dẫn login không yêu cầu xác thực
                        .anyRequest().authenticated()  // Các yêu cầu khác đều yêu cầu xác thực
                );
        // Áp dụng JwtConfigurer
        jwtConfigurer.configure(http);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);  // Đảm bảo sử dụng PasswordEncoder
        return authenticationManagerBuilder.build();
    }

}

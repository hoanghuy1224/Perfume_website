package com.gmail.HoangHuy.ecommerce.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {
    @Autowired
    private  JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtProvider.resolveToken((HttpServletRequest) servletRequest);

        try {
            if (token != null && jwtProvider.validateToken(token)) {
                Authentication authentication = jwtProvider.getAuthentication(token);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            // Gửi lỗi HTTP nếu token không hợp lệ
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            throw new JwtAuthenticationException("Mã thông báo JWT đã hết hạn hoặc không hợp lệ");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

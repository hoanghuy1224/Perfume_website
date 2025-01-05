package com.gmail.HoangHuy.ecommerce.controller;

import com.gmail.HoangHuy.ecommerce.model.Perfume;
import com.gmail.HoangHuy.ecommerce.model.User;
import com.gmail.HoangHuy.ecommerce.dto.AuthenticationRequestDTO;
import com.gmail.HoangHuy.ecommerce.dto.PasswordResetDto;
import com.gmail.HoangHuy.ecommerce.security.JwtProvider;
import com.gmail.HoangHuy.ecommerce.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/rest")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtProvider jwtProvider;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, UserService userService, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            User user = userService.findByEmail(request.getEmail());
            String userRole = user.getRoles().iterator().next().name();
            String token = jwtProvider.createToken(request.getEmail(), userRole);
            List<Perfume> perfumeList = user.getPerfumeList();

            Map<Object, Object> response = new HashMap<>();
            response.put("email", request.getEmail());
            response.put("token", token);
            response.put("userRole", userRole);
            response.put("perfumeList", perfumeList);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Mật khẩu hoặc email không đúng", HttpStatus.FORBIDDEN);
        }
    }


    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetDto passwordReset) {
        boolean forgotPassword = userService.sendPasswordResetCode(passwordReset.getEmail());

        if (!forgotPassword) {
            return new ResponseEntity<>("Không tìm thấy email", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Mã đặt lại mật khẩu được gửi đến E-mail của bạn", HttpStatus.OK);
    }

    @GetMapping("/reset/{code}")
    public ResponseEntity<?> getPasswordResetCode(@PathVariable String code) {
        User user = userService.findByPasswordResetCode(code);

        if (user == null) {
            return new ResponseEntity<>("Mã đặt lại mật khẩu không hợp lệ!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> passwordReset(@RequestBody PasswordResetDto passwordReset) {
        Map<String, String> errors = new HashMap<>();
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordReset.getPassword2());
        boolean isPasswordDifferent = passwordReset.getPassword() != null &&
                !passwordReset.getPassword().equals(passwordReset.getPassword2());

        if (isConfirmEmpty) {
            errors.put("password2Error", "Xác nhận mật khẩu không được để trống");

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (isPasswordDifferent) {
            errors.put("passwordError", "Mật khẩu không khớp");

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        userService.passwordReset(passwordReset);

        return new ResponseEntity<>("Đã thay đổi mật khẩu thành công!", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}

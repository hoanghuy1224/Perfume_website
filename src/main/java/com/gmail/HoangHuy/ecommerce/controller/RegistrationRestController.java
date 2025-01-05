package com.gmail.HoangHuy.ecommerce.controller;

import com.gmail.HoangHuy.ecommerce.model.User;
import com.gmail.HoangHuy.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rest")
public class RegistrationRestController {

    private final UserService userService;

    @Autowired
    public RegistrationRestController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(
            @RequestParam("password2") String passwordConfirm,
            @Valid User user

    ) {
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        boolean isPasswordDifferent = user.getPassword() != null && !user.getPassword().equals(passwordConfirm);
        Map<String, String> errors = new HashMap<>();

        if (isConfirmEmpty) {
            errors.put("password2Error", "Xác nhận mật khẩu không được để trống");

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (isPasswordDifferent) {
            errors.put("passwordError", "Mật khẩu không khớp");

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (!userService.addUser(user)) {
            errors.put("emailError", "Email đã được sử dụng");

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<?> activateEmailCode(@PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            return new ResponseEntity<>("kích hoạt thành công", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Không tìm thấy mã kích hoạt", HttpStatus.NOT_FOUND);
        }
    }
}

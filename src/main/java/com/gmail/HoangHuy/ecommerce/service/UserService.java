package com.gmail.HoangHuy.ecommerce.service;

import com.gmail.HoangHuy.ecommerce.model.Review;
import com.gmail.HoangHuy.ecommerce.model.User;
import com.gmail.HoangHuy.ecommerce.dto.PasswordResetDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    User getOne(Long id);

    User findByEmail(String email);

    List<User> findAll();

    User findByUsername(String username);

    User findByActivationCode(String code);

    User findByPasswordResetCode(String code);

    User save(User user);

    boolean addUser(User user);

    boolean activateUser(String code);

    void sendMessage(User user, List<String> emailMessages, String subject, String code, String urlPart);

    boolean sendPasswordResetCode(String email);

    void passwordReset(PasswordResetDto passwordReset);

    void userSave(String username, Map<String, String> form, User user);

    void updateProfile(User user, String password, String email);

    void addReviewToPerfume(Review review, Long perfumeId);
}
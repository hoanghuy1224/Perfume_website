package com.gmail.HoangHuy.ecommerce.service.Impl;

import com.gmail.HoangHuy.ecommerce.model.Perfume;
import com.gmail.HoangHuy.ecommerce.model.Review;
import com.gmail.HoangHuy.ecommerce.model.Role;
import com.gmail.HoangHuy.ecommerce.model.User;
import com.gmail.HoangHuy.ecommerce.dto.PasswordResetDto;
import com.gmail.HoangHuy.ecommerce.repository.PerfumeRepository;
import com.gmail.HoangHuy.ecommerce.repository.ReviewRepository;
import com.gmail.HoangHuy.ecommerce.repository.UserRepository;
import com.gmail.HoangHuy.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  MailSender mailSender;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  PerfumeRepository perfumeRepository;
    @Autowired
    private  ReviewRepository reviewRepository;

    @Value("${hostname}")
    private String hostname;

    @Override
    public User getOne(Long id) {
        return userRepository.getOne(id);
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByActivationCode(String code) {
        return userRepository.findByActivationCode(code);
    }

    @Override
    public User findByPasswordResetCode(String code){
        return userRepository.findByPasswordResetCode(code);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, LockedException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng");
        }

        if (user.getActivationCode() != null) {
            throw new LockedException("email chưa được kích hoạt");
        }

        return user;
    }

    @Override
    public boolean addUser(User user) {
        User userFromDb = userRepository.findByEmail(user.getEmail());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        String subject = "Perfume Store";
        List<String> emailMessages = new ArrayList<>();
        emailMessages.add("Đăng kí tài khoản thành công");
        emailMessages.add("Chào mừng đến với cửa hàng trực tuyến Perfume.");

        sendMessage(user, emailMessages, subject, user.getActivationCode(), "activate");

        return true;
    }

    @Override
    public boolean sendPasswordResetCode(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return false;
        }

        user.setPasswordResetCode(UUID.randomUUID().toString());
        userRepository.save(user);

        String subject = "Khôi phục mật khẩu";
        List<String> emailMessages = new ArrayList<>();
        emailMessages.add("Chúng tôi đã nhận được yêu cầu khôi phục mật khẩu cho tài khoản của bạn.");
        emailMessages.add("Để khôi phục mật khẩu, vui lòng làm theo liên kết này ");

        sendMessage(user, emailMessages, subject, user.getPasswordResetCode(), "reset");

        return true;
    }

    @Override
    public void sendMessage(User user, List<String> emailMessages, String subject, String code, String urlPart) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s! \n" + "%s \n" + "%s http://%s/%s/%s",
                    user.getUsername(),
                    emailMessages.get(0),
                    emailMessages.get(1),
                    hostname,
                    urlPart,
                    code
            );

            mailSender.send(user.getEmail(), subject, message);
        }
    }

    @Override
    public void passwordReset(PasswordResetDto passwordReset) {
        User user = userRepository.findByEmail(passwordReset.getEmail());
        user.setPassword(passwordEncoder.encode(passwordReset.getPassword()));
        user.setPasswordResetCode(null);

        userRepository.save(user);
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);

        return true;
    }

    @Override
    public void userSave(String username, Map<String, String> form, User user) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    @Override
    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepository.save(user);

    }

    @Override
    public void addReviewToPerfume(Review review, Long perfumeId) {
        Perfume perfume = perfumeRepository.getOne(perfumeId);
        Review perfumeReview = new Review();
        perfumeReview.setAuthor(review.getAuthor());
        perfumeReview.setMessage(review.getMessage());
        perfumeReview.setDate(LocalDate.now());
        perfume.getReviews().add(perfumeReview);

        reviewRepository.save(perfumeReview);
    }
}
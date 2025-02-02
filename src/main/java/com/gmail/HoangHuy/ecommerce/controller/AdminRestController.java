package com.gmail.HoangHuy.ecommerce.controller;

import com.gmail.HoangHuy.ecommerce.model.Order;
import com.gmail.HoangHuy.ecommerce.model.Perfume;
import com.gmail.HoangHuy.ecommerce.model.User;
import com.gmail.HoangHuy.ecommerce.service.OrderService;
import com.gmail.HoangHuy.ecommerce.service.PerfumeService;
import com.gmail.HoangHuy.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rest")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminRestController {

    @Value("${upload.path}")
    private String uploadPath;

    private final UserService userService;

    private final PerfumeService perfumeService;

    private final OrderService orderService;

    @Autowired
    public AdminRestController(UserService userService, PerfumeService perfumeService, OrderService orderService) {
        this.userService = userService;
        this.perfumeService = perfumeService;
        this.orderService = orderService;
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> addPerfume(@Valid Perfume perfume, BindingResult bindingResult, @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        } else {
            saveFile(perfume, file);

            Perfume savedPerfume = perfumeService.save(perfume);

            return new ResponseEntity<>(savedPerfume, HttpStatus.CREATED);
        }
    }

    @PutMapping("/admin/edit")
    public ResponseEntity<?> updatePerfume(@Valid Perfume perfume, BindingResult bindingResult, @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        } else {
            saveFile(perfume, file);

            perfumeService.saveProductInfoById(perfume.getPerfumeTitle(), perfume.getPerfumer(), perfume.getYear(), perfume.getCountry(), perfume.getPerfumeGender(), perfume.getFragranceTopNotes(), perfume.getFragranceMiddleNotes(), perfume.getFragranceBaseNotes(), perfume.getDescription(), perfume.getFilename(), perfume.getPrice(), perfume.getVolume(), perfume.getType(), perfume.getId());

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderService.findAll();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/admin/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long userId) {
        User user = userService.getOne(userId);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/admin/user/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/admin/user/edit")
    public ResponseEntity<?> updateUser(@RequestParam String username, @RequestParam Map<String, String> form, @RequestParam("userId") User user) {
        userService.userSave(username, form, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void saveFile(Perfume perfume, @RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            perfume.setFilename("empty.jpg");
        } else {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            perfume.setFilename(resultFilename);
        }
    }
}

package com.gmail.HoangHuy.ecommerce.service;

import com.gmail.HoangHuy.ecommerce.model.Order;
import com.gmail.HoangHuy.ecommerce.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    List<Order> findAll();

    Order save(Order order);

    List<Order> findOrderByUser(User user);

    Order postOrder(Order validOrder, User userSession);
}

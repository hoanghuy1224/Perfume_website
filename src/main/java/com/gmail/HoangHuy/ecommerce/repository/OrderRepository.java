package com.gmail.HoangHuy.ecommerce.repository;

import com.gmail.HoangHuy.ecommerce.model.Order;
import com.gmail.HoangHuy.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrderByUser(User user);
}
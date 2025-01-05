package com.gmail.HoangHuy.ecommerce.service.Impl;

import com.gmail.HoangHuy.ecommerce.model.Order;
import com.gmail.HoangHuy.ecommerce.model.User;
import com.gmail.HoangHuy.ecommerce.repository.OrderRepository;
import com.gmail.HoangHuy.ecommerce.repository.UserRepository;
import com.gmail.HoangHuy.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final MailSender mailSender;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, MailSender mailSender) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findOrderByUser(User user) {
        return orderRepository.findOrderByUser(user);
    }

    @Override
    public Order postOrder(Order validOrder, User userSession) {
        User user = userRepository.findByEmail(userSession.getEmail());
        Order order = new Order(user);

        order.getPerfumeList().addAll(user.getPerfumeList());
        order.setTotalPrice(validOrder.getTotalPrice());
        order.setFirstName(validOrder.getFirstName());
        order.setLastName(validOrder.getLastName());
        order.setCity(validOrder.getCity());
        order.setAddress(validOrder.getAddress());
        order.setPostIndex(validOrder.getPostIndex());
        order.setEmail(validOrder.getEmail());
        order.setPhoneNumber(validOrder.getPhoneNumber());
        user.getPerfumeList().clear();

        orderRepository.save(order);

        StringBuilder perfumes = new StringBuilder();
        order.getPerfumeList().forEach((perfume) ->
        {
            perfumes.append(perfume.getPerfumer());
            perfumes.append(" ");
            perfumes.append(perfume.getPerfumeTitle());
            perfumes.append(" — $");
            perfumes.append(perfume.getPrice());
            perfumes.append(".00");
            perfumes.append("\n");
        });

        String subject = "Đơn hàng #" + order.getId();
        String message = "Chào " + order.getFirstName() + "!\n" +
                "Cảm ơn bạn đã đặt hàng tại cửa hàng nước hoa online.\n" +
                "Số đơn hàng của bạn là " + order.getId() + "\n" +
                "Ngày: " + order.getDate() + "\n" +
                "Tên: " + order.getFirstName() + " " + order.getLastName() + "\n" +
                "Địa chỉ: " + order.getCity() + ", " + order.getAddress() + "\n" +
                "Mã bưu điện: " + order.getPostIndex() + "\n" +
                "Số điện thoại: " + order.getPhoneNumber() + "\n" +
                "Sản phẩm nước hoa: " + "\n" + perfumes + "\n" +
                "Tổng giá trị: $" + order.getTotalPrice();

        mailSender.send(order.getEmail(), subject, message);

        return order;
    }
}

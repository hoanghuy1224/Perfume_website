package com.gmail.HoangHuy.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "user", "perfumeList"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double totalPrice;

    private LocalDate date;

    @NotBlank(message = "Vui lòng điền vào trường này")
    private String firstName;

    @NotBlank(message = "Vui lòng điền vào trường này")
    private String lastName;

    @NotBlank(message = "Vui lòng điền vào trường này")
    private String city;

    @NotBlank(message = "Vui lòng điền vào trường này")
    private String address;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phoneNumber;

    @NotNull(message = "Mã bưu điện không được để trống")
    @Min(value = 10000, message = "Mã bưu điện phải có ít nhất 5 chữ số")
    private Integer postIndex;

    // liên kết giữa Order và Perfume
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_perfume",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "perfume_id"))
    @OrderColumn(name = "list_order")
    private List<Perfume> perfumeList;

    @ManyToOne
    private User user;

    public Order(User user) {
        this.date = LocalDate.now();
        this.user = user;
        this.perfumeList = new ArrayList<>();
    }
}


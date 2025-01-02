package com.gmail.HoangHuy.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên tác giả không được để trống")
    private String author;

    @NotBlank(message = "Nội dung không được để trống")
    private String message;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "perfume_id", nullable = false)
    private Perfume perfume;
}


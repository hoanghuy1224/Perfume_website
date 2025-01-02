package com.gmail.HoangHuy.ecommerce.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Perfume")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Perfume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên nước hoa là bắt buộc")
    @Size(max = 255, message = "Tên nước hoa phải ít hơn 255 ký tự")
    private String perfumeTitle;

    @NotBlank(message = "Tên nhà sản xuất là bắt buộc")
    @Size(max = 255, message = "Tên nhà sản xuất phải ít hơn 255 ký tự")
    private String perfumer;

    @NotNull(message = "Năm sản xuất là bắt buộc")
    @Min(value = 1900, message = "Năm sản xuất phải hợp lệ")
    @Max(value = 2100, message = "Năm sản xuất phải hợp lệ")
    private Integer year;

    @NotBlank(message = "Tên quốc gia là bắt buộc")
    @Size(max = 255, message = "Tên quốc gia phải ít hơn 255 ký tự")
    private String country;

    @NotBlank(message = "Giới tính nước hoa là bắt buộc")
    @Size(max = 255, message = "Giới tính nước hoa phải ít hơn 255 ký tự")
    private String perfumeGender;

    @NotBlank(message = "Hương đầu là bắt buộc")
    @Size(max = 255, message = "Hương đầu phải ít hơn 255 ký tự")
    private String fragranceTopNotes;

    @NotBlank(message = "Hương giữa là bắt buộc")
    @Size(max = 255, message = "Hương giữa phải ít hơn 255 ký tự")
    private String fragranceMiddleNotes;

    @NotBlank(message = "Hương cuối là bắt buộc")
    @Size(max = 255, message = "Hương cuối phải ít hơn 255 ký tự")
    private String fragranceBaseNotes;

    private String description;

    private String filename;

    @NotNull(message = "Giá bán là bắt buộc")
    @Min(value = 0, message = "Giá bán phải lớn hơn hoặc bằng 0")
    private Integer price;

    @NotBlank(message = "Dung tích là bắt buộc")
    @Size(max = 255, message = "Dung tích phải ít hơn 255 ký tự")
    private String volume;

    @NotBlank(message = "Loại nước hoa là bắt buộc")
    @Size(max = 255, message = "Loại nước hoa phải ít hơn 255 ký tự")
    private String type;

    @OneToMany(mappedBy = "perfume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}


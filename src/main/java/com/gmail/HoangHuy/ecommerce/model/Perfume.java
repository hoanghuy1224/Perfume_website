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
@EqualsAndHashCode(of = {"id", "perfumer", "perfumeTitle", "perfumeGender", "price"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Perfume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Perfume title is required")
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String perfumeTitle;

    @NotBlank(message = "Perfumer name is required")
    @Size(max = 255, message = "Perfumer name must be less than 255 characters")
    private String perfumer;

    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be a valid year")
    @Max(value = 2100, message = "Year must be a valid year")
    private Integer year;

    @NotBlank(message = "Country name is required")
    @Size(max = 255, message = "Country name must be less than 255 characters")
    private String country;

    @NotBlank(message = "Perfume gender is required")
    @Size(max = 255, message = "Gender must be less than 255 characters")
    private String perfumeGender;

    @NotBlank(message = "Fragrance top notes are required")
    @Size(max = 255, message = "Fragrance top notes must be less than 255 characters")
    private String fragranceTopNotes;

    @NotBlank(message = "Fragrance middle notes are required")
    @Size(max = 255, message = "Fragrance middle notes must be less than 255 characters")
    private String fragranceMiddleNotes;

    @NotBlank(message = "Fragrance base notes are required")
    @Size(max = 255, message = "Fragrance base notes must be less than 255 characters")
    private String fragranceBaseNotes;

    private String description;

    private String filename;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Integer price;

    @NotBlank(message = "Volume is required")
    @Size(max = 255, message = "Volume must be less than 255 characters")
    private String volume;

    @NotBlank(message = "Type is required")
    @Size(max = 255, message = "Type must be less than 255 characters")
    private String type;

    @OneToMany(mappedBy = "perfume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}

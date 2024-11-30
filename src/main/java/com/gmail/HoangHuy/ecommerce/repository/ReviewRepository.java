package com.gmail.HoangHuy.ecommerce.repository;

import com.gmail.HoangHuy.ecommerce.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}

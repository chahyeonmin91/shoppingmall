package com.wassupshoppingmall.domain.review.repository;

import com.wassupshoppingmall.domain.product.entity.Product;
import com.wassupshoppingmall.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
}

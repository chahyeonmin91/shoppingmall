package com.wassupshoppingmall.domain.review.controller;

import com.wassupshoppingmall.domain.review.dto.ReviewRequest;
import com.wassupshoppingmall.domain.review.dto.ReviewResponse;
import com.wassupshoppingmall.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/{productId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@PathVariable Long productId, @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.createReview(productId, request));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long productId,@PathVariable Long reviewId ,@RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.updateReview(productId, reviewId, request));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviews(productId));
    }
}

package com.wassupshoppingmall.domain.review.service;

import com.wassupshoppingmall.domain.product.dto.ProductResponse;
import com.wassupshoppingmall.domain.product.entity.Product;
import com.wassupshoppingmall.domain.product.repository.ProductRepository;
import com.wassupshoppingmall.domain.review.dto.ReviewRequest;
import com.wassupshoppingmall.domain.review.dto.ReviewResponse;
import com.wassupshoppingmall.domain.review.entity.Review;
import com.wassupshoppingmall.domain.review.repository.ReviewRepository;
import com.wassupshoppingmall.domain.user.entity.User;
import com.wassupshoppingmall.global.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private final AuthService authService;

    public ReviewResponse createReview(Long productId, ReviewRequest request) {
        User user = authService.getLoginUser();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));
        Review review = Review.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .user(user)
                .product(product)
                .build();
        reviewRepository.save(review);

        return new ReviewResponse(
                review.getId(),
                review.getContent(),
                review.getRating(),
                user.getNickname(),
                review.getCreatedAt()
        );
    }
    public ReviewResponse updateReview(Long productId, Long reviewId, ReviewRequest request) {
        User user = authService.getLoginUser();
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("본인이 작성한 리뷰만 수정할 수 있습니다");
        }

        review.updateReview(request.getContent(), request.getRating());

        return new ReviewResponse(
                review.getId(),
                review.getContent(),
                review.getRating(),
                user.getNickname(),
                review.getCreatedAt()
        );
    }

    public void deleteReview(Long reviewId) {
        User user = authService.getLoginUser();
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다"));
        if (!review.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("본인이 작성한 리뷰만 삭제할 수 있습니다");
        }
        reviewRepository.delete(review);
    }

    public List<ReviewResponse> getReviews(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다"));

        return reviewRepository.findByProduct(product).stream()
                .map(r -> new ReviewResponse(
                        r.getId(),
                        r.getContent(),
                        r.getRating(),
                        r.getUser().getNickname(),
                        r.getCreatedAt()
                )).toList();
    }
}

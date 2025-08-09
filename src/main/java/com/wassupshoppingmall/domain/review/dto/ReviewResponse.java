package com.wassupshoppingmall.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private String content;
    private Integer rating;
    private String nickname;
    private LocalDateTime createdAt;
}

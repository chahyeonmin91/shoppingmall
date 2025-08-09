package com.wassupshoppingmall.domain.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {
    private String content;
    private Integer rating;
}
